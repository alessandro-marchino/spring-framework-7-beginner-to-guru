package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import guru.springframework.spring7restmvc.TestUtils;
import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.events.BeerCreatedEvent;
import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest
@Slf4j
@RecordApplicationEvents
public class BeerControllerIT {
	@Autowired BeerController controller;
	@Autowired BeerRepository repository;
	@Autowired BeerMapper mapper;
	@Autowired WebApplicationContext wac;
	@Autowired JsonMapper jsonMapper;
	@Autowired ApplicationEvents applicationEvents;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
			.apply(springSecurity())
			.build();
	}

	@Test
	void testListBeersByName() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.queryParam("beerName", "IPA")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(25)))
			.andExpect(jsonPath("$.page.totalElements", is(336)))
			.andExpect(jsonPath("$.page.totalPages", is(14)));
	}
	@Test
	void testListBeersByStyle() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.queryParam("beerStyle", BeerStyle.IPA.toString())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(25)))
			.andExpect(jsonPath("$.page.totalElements", is(548)))
			.andExpect(jsonPath("$.page.totalPages", is(22)));
	}
	@Test
	void testListBeersByNameAndStyle() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.queryParam("beerName", "IPA")
				.queryParam("beerStyle", BeerStyle.IPA.toString())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(25)))
			.andExpect(jsonPath("$.page.totalElements", is(310)))
			.andExpect(jsonPath("$.page.totalPages", is(13)))
			.andExpect(jsonPath("$.content[0].quantityOnHand", nullValue()));
	}
	@Test
	void testListBeersByNameAndStyleShowInventory() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.queryParam("beerName", "IPA")
				.queryParam("beerStyle", BeerStyle.IPA.toString())
				.queryParam("showInventory", "true")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(25)))
			.andExpect(jsonPath("$.page.totalElements", is(310)))
			.andExpect(jsonPath("$.page.totalPages", is(13)))
			.andExpect(jsonPath("$.content[0].quantityOnHand", notNullValue()));
	}
	@Test
	void testListBeersByNameAndStyleNotShowInventory() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.queryParam("beerName", "IPA")
				.queryParam("beerStyle", BeerStyle.IPA.toString())
				.queryParam("showInventory", "false")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(25)))
			.andExpect(jsonPath("$.page.totalElements", is(310)))
			.andExpect(jsonPath("$.page.totalPages", is(13)))
			.andExpect(jsonPath("$.content[0].quantityOnHand", nullValue()));
	}

	@Test
	void testListBeersByNameAndStyleShowInventoryPage2() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.queryParam("beerName", "IPA")
				.queryParam("beerStyle", BeerStyle.IPA.toString())
				.queryParam("showInventory", "true")
				.queryParam("pageNumber", "2")
				.queryParam("pageSize", "50")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(50)))
			.andExpect(jsonPath("$.page.totalElements", is(310)))
			.andExpect(jsonPath("$.page.totalPages", is(7)))
			.andExpect(jsonPath("$.content[0].quantityOnHand", notNullValue()));
	}
	@Test
	void testListBeersNoAuth() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.queryParam("beerName", "IPA")
			)
			.andExpect(status().isUnauthorized());
	}
	@Test
	void testListBeersWrongAuth() throws Exception {
		mockMvc.perform(get(BeerController.PATH)
				.with(httpBasic("wrong", "user"))
				.queryParam("beerName", "IPA")
			)
			.andExpect(status().isUnauthorized());
	}

    @Test
	@Transactional
	@Rollback
    void testDeleteBeer() {
		Beer beer = repository.findAll(Pageable.ofSize(1)).getContent().getFirst();
		ResponseEntity<Void> responseEntity = controller.deleteBeer(beer.getId());
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));

		Optional<Beer> foundBeer = repository.findById(beer.getId());
		assertThat(foundBeer).isEmpty();
    }

	@Test
    void testDeleteBeerNotFound() {
		assertThrows(NotFoundException.class, () -> controller.deleteBeer(UUID.randomUUID()));
    }

    @Test
    void testGetBeerById() {
		Beer beer = repository.findAll(Pageable.ofSize(1)).getContent().getFirst();
		BeerDTO dto = controller.getBeerById(beer.getId());

		assertThat(dto).isNotNull();
    }

	@Test
    void testGetBeerByIdNotFound() {
		assertThrows(NotFoundException.class, () -> controller.getBeerById(UUID.randomUUID()));
    }

    @Test
    void testListBeers() {
		Page<BeerDTO> dtos = controller.listBeers(null, null, false, 1, 2500);
		assertThat(dtos).hasSize(1000);
    }

	@Test
	@Transactional
	@Rollback
    void testListBeersEmpty() {
		repository.deleteAll();
		Page<BeerDTO> dtos = controller.listBeers(null, null, false, 1, 25);
		assertThat(dtos).hasSize(0);
    }

	@Test
	void testPatchBeerNotFound() {
		assertThrows(NotFoundException.class, () -> controller.patchBeer(UUID.randomUUID(), BeerDTO.builder().build()));
	}

    @Test
	@Transactional
	@Rollback
    void testPatchBeer() {
		Beer beer = repository.findAll(Pageable.ofSize(1)).getContent().getFirst();
		BeerDTO dto = mapper.beerToBeerDto(beer);
		final String beerName = "UPDATED";
		dto.setBeerName(beerName);

		ResponseEntity<Void> responseEntity = controller.patchBeer(beer.getId(), dto);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
		repository.flush();

		Beer updatedBeer = repository.findById(beer.getId()).get();
		assertThat(updatedBeer).isNotNull();
		assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
		assertThat(updatedBeer.getVersion()).isEqualTo(1);
    }

	@Test
    void testPatchBadNameBeer() throws Exception {
		Beer beer = repository.findAll(Pageable.ofSize(1)).getContent().getFirst();
		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Name 12345678901234567890123456789012345678901234567890");

		mockMvc.perform(patch(BeerController.PATH_ID, beer.getId())
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beerMap)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
	@Transactional
	@Rollback
    void testSaveBeer() {
		BeerDTO dto = BeerDTO.builder()
			.beerName("New Beer")
			.build();
		ResponseEntity<Void> responseEntity = controller.saveBeer(dto);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
		assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
		String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
		UUID savedUUID = UUID.fromString(locationUUID[4]);

		Beer beer = repository.findById(savedUUID).get();
		assertThat(beer).isNotNull();
		assertThat(beer.getBeerName()).isEqualTo("New Beer");
		assertThat(beer.getVersion()).isEqualTo(0);
    }

	@Test
    void testSaveBeerMvc() throws Exception {
		BeerDTO dto = BeerDTO.builder()
			.beerName("New Beer")
			.beerStyle(BeerStyle.IPA)
			.upc("123456")
			.price(BigDecimal.TEN)
			.quantityOnHand(5)
			.build();

		mockMvc.perform(post(BeerController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isCreated())
			.andReturn();

		assertThat(applicationEvents.stream(BeerCreatedEvent.class).count()).isEqualTo(1);
    }

	@Test
    void testUpdateBeerNotFound() {
		assertThrows(NotFoundException.class, () -> controller.updateBeer(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Test
	@Transactional
	@Rollback
    void testUpdateBeer() {
		Beer beer = repository.findAll(Pageable.ofSize(1)).getContent().getFirst();
		BeerDTO dto = mapper.beerToBeerDto(beer);
		final String beerName = "UPDATED";
		dto.setBeerName(beerName);

		ResponseEntity<Void> responseEntity = controller.updateBeer(beer.getId(), dto);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
		repository.flush();

		Beer updatedBeer = repository.findById(beer.getId()).get();
		assertThat(updatedBeer).isNotNull();
		assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
		assertThat(updatedBeer.getVersion()).isEqualTo(1);
    }

	@Test
	void testUpdateBeerBadVersion() throws Exception {
		Beer beer = repository.findAll(Pageable.ofSize(1)).getContent().getFirst();
		BeerDTO beerDTO = mapper.beerToBeerDto(beer);
		beerDTO.setBeerName("Updated Name");
		MvcResult result = mockMvc.perform(put(BeerController.PATH_ID, beer.getId())
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beerDTO)))
			.andExpect(status().isNoContent())
			.andReturn();
		log.warn("First response: {}", result.getResponse().getStatus());
		beerDTO.setBeerName("Updated Name 2");
		MvcResult result2 = mockMvc.perform(put(BeerController.PATH_ID, beer.getId())
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(beerDTO)))
			.andExpect(status().isNoContent())
			.andReturn();
		log.warn("Second response: {}", result2.getResponse().getStatus());
	}
}
