package guru.springframework.spring7restmvc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.service.BeerService;
import guru.springframework.spring7restmvc.service.impl.BeerServiceImpl;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(BeerController.class)
@ExtendWith(MockitoExtension.class)
class BeerControllerTest {
	@Autowired MockMvc mockMvc;
	@Autowired JsonMapper jsonMapper;
	@MockitoBean BeerService beerService;

	@Captor ArgumentCaptor<UUID> uuidArgumentCaptor;
	@Captor ArgumentCaptor<BeerDTO> beerArgumentCaptor;

	private BeerServiceImpl beerServiceImpl;

	@BeforeEach
	void setUp() {
		beerServiceImpl = new BeerServiceImpl();
	}

	@Test
	void testGetBeerById() throws Exception {
		BeerDTO testBeer = beerServiceImpl.listBeers(null, null).get(0);
		given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

		mockMvc.perform(get(BeerController.PATH_ID, testBeer.getId())
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
		.andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}

	@Test
	void testGetBeerByIdNotFound() throws Exception {
		given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

		mockMvc.perform(get(BeerController.PATH_ID, UUID.randomUUID())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	void testlistBeers() throws Exception {
		List<BeerDTO> beers = beerServiceImpl.listBeers(null, null);
		given(beerService.listBeers(null, null)).willReturn(beers);

		mockMvc.perform(get(BeerController.PATH)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void testCreateNewBeer() throws Exception {
		BeerDTO resultBeer = beerServiceImpl.listBeers(null, null).get(0);
		BeerDTO testBeer = BeerDTO.builder()
			.beerName(resultBeer.getBeerName())
			.beerStyle(resultBeer.getBeerStyle())
			.price(resultBeer.getPrice())
			.quantityOnHand(resultBeer.getQuantityOnHand())
			.upc(resultBeer.getUpc())
			.build();

		given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(resultBeer);
		mockMvc.perform(post(BeerController.PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(testBeer)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/v1/beer/" + resultBeer.getId()));
	}

	@Test
	void testCreateNewBeerNullBeerName() throws Exception {
		BeerDTO testBeer = BeerDTO.builder()
			.quantityOnHand(-2)
			.build();

		MvcResult result = mockMvc.perform(post(BeerController.PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(testBeer)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.length()", is(7)))
			.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString()).isNotBlank();
		verify(beerService, never()).saveNewBeer(any());
	}

	@Test
	void testUpdateBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers(null, null).get(0);
		given(beerService.updateBeerById(eq(beer.getId()), any(BeerDTO.class))).willReturn(Optional.of(beer));

		mockMvc.perform(put(BeerController.PATH_ID, beer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beer)))
			.andExpect(status().isNoContent());
		verify(beerService).updateBeerById(eq(beer.getId()), any(BeerDTO.class));
	}

	@Test
	void testUpdateBeerNullBeerName() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers(null, null).get(0);
		BeerDTO testBeer = BeerDTO.builder()
			.quantityOnHand(-2)
			.build();

		MvcResult result = mockMvc.perform(put(BeerController.PATH_ID, beer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(testBeer)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.length()", is(7)))
			.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getContentAsString()).isNotBlank();
		verify(beerService, never()).saveNewBeer(any());
	}

	@Test
	void testUpdateBeerNotFound() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers(null, null).get(0);
		given(beerService.updateBeerById(eq(beer.getId()), any(BeerDTO.class))).willReturn(Optional.empty());

		mockMvc.perform(put(BeerController.PATH_ID, beer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beer)))
			.andExpect(status().isNotFound());
		verify(beerService).updateBeerById(eq(beer.getId()), any(BeerDTO.class));
	}

	@Test
	void testDeleteBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers(null, null).get(0);
		given(beerService.deleteById(eq(beer.getId()))).willReturn(true);

		mockMvc.perform(delete(BeerController.PATH_ID, beer.getId()))
			.andExpect(status().isNoContent());
		verify(beerService).deleteById(uuidArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
	}

	@Test
	void testDeleteBeerNotFound() throws Exception {
		UUID uuid = UUID.randomUUID();
		given(beerService.deleteById(any(UUID.class))).willReturn(false);

		mockMvc.perform(delete(BeerController.PATH_ID, uuid))
			.andExpect(status().isNotFound());
		verify(beerService).deleteById(uuidArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(uuid);
	}

	@Test
	void testPatchBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers(null, null).get(0);
		Map<String, Object> beerMap = Map.of("beerName", "New Name");

		given(beerService.patchBeerById(eq(beer.getId()), any(BeerDTO.class))).willReturn(Optional.of(beer));

		mockMvc.perform(patch(BeerController.PATH_ID, beer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beerMap)))
			.andExpect(status().isNoContent());
		verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
		assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo(beerMap.get("beerName"));
	}

	@Test
	void testPatchBeerNotFound() throws Exception {
		UUID uuid = UUID.randomUUID();
		Map<String, Object> beerMap = Map.of("beerName", "New Name");

		given(beerService.patchBeerById(eq(uuid), any(BeerDTO.class))).willReturn(Optional.empty());

		mockMvc.perform(patch(BeerController.PATH_ID,uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beerMap)))
			.andExpect(status().isNotFound());
		verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(uuid);
		assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo(beerMap.get("beerName"));
	}
}
