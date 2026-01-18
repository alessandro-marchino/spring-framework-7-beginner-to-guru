package guru.springframework.spring7restmvc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import guru.springframework.spring7restmvc.service.impl.BeerServiceImpl;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	@Autowired MockMvc mockMvc;
	@Autowired JsonMapper jsonMapper;
	@MockitoBean BeerService beerService;

	private BeerServiceImpl beerServiceImpl;

	@BeforeEach
	void setUp() {
		beerServiceImpl = new BeerServiceImpl();
	}

	@Test
	void testGetBeerById() throws Exception {
		Beer testBeer = beerServiceImpl.listBeers().get(0);
		given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

		mockMvc.perform(get("/api/v1/beer/{beerId}", testBeer.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
			.andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}

	@Test
	void testlistBeers() throws Exception {
		List<Beer> beers = beerServiceImpl.listBeers();
		given(beerService.listBeers()).willReturn(beers);

		mockMvc.perform(get("/api/v1/beer")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void testCreateNewBeer() throws Exception {
		Beer resultBeer = beerServiceImpl.listBeers().get(0);
		Beer testBeer = Beer.builder()
			.beerName(resultBeer.getBeerName())
			.beerStyle(resultBeer.getBeerStyle())
			.price(resultBeer.getPrice())
			.quantityOnHand(resultBeer.getQuantityOnHand())
			.upc(resultBeer.getUpc())
			.build();

		given(beerService.saveNewBeer(any(Beer.class))).willReturn(resultBeer);
		mockMvc.perform(post("/api/v1/beer")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(testBeer)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/v1/beer/" + resultBeer.getId()));
	}

	@Test
	void testUpdateBeer() throws Exception {
		Beer beer = beerServiceImpl.listBeers().get(0);

		mockMvc.perform(put("/api/v1/beer/{beerId}", beer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beer)))
			.andExpect(status().isNoContent());
		verify(beerService).updateBeerById(eq(beer.getId()), any(Beer.class));
	}

	@Test
	void testDeleteBeer() throws Exception {
		Beer beer = beerServiceImpl.listBeers().get(0);

		mockMvc.perform(delete("/api/v1/beer/{beerId}", beer.getId()))
			.andExpect(status().isNoContent());
		ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
		verify(beerService).deleteById(uuidArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
	}
}
