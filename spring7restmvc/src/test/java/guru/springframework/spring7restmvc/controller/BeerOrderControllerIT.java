package guru.springframework.spring7restmvc.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import guru.springframework.spring7restmvc.TestUtils;
import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.BeerOrder;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderLineCreateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderLineUpdateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderShipmentUpdateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderUpdateDTO;
import guru.springframework.spring7restmvc.repositories.BeerOrderRepository;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest
class BeerOrderControllerIT {
	@Autowired WebApplicationContext wac;
	@Autowired BeerOrderRepository repository;
	@Autowired CustomerRepository customerRepository;
	@Autowired BeerRepository beerRepository;
	@Autowired JsonMapper jsonMapper;
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
			.apply(springSecurity())
			.build();
	}

	@Test
	void testListBeerOrders() throws Exception {
		mockMvc.perform(get(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", greaterThan(0)));
	}

	@Test
	void testGetBeerOrderById() throws Exception {
		BeerOrder beerOrder = repository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();
		mockMvc.perform(get(BeerOrderController.PATH_ID, beerOrder.getId())
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(beerOrder.getId().toString())))
			.andExpect(jsonPath("$.customer.id", is(beerOrder.getCustomer().getId().toString())));
	}

	@Test
	void testCreateBeerOrder() throws Exception {
		Beer beer = beerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();
		Customer customer = customerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();

		BeerOrderCreateDTO dto = BeerOrderCreateDTO.builder()
			.customerId(customer.getId())
			.beerOrderLines(Set.of(
				BeerOrderLineCreateDTO.builder()
					.beerId(beer.getId())
					.orderQuantity(1)
					.build()
			))
			.build();
		mockMvc.perform(post(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"));
	}

	@Test
	void testCreateBeerOrderNoCustomer() throws Exception {
		Beer beer = beerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();

		BeerOrderCreateDTO dto = BeerOrderCreateDTO.builder()
			.beerOrderLines(Set.of(
				BeerOrderLineCreateDTO.builder()
					.beerId(beer.getId())
					.orderQuantity(1)
					.build()
			))
			.build();
		mockMvc.perform(post(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}
	@Test
	void testCreateBeerOrderNoBeer() throws Exception {
		Customer customer = customerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();

		BeerOrderCreateDTO dto = BeerOrderCreateDTO.builder()
			.customerId(customer.getId())
			.beerOrderLines(Set.of(
				BeerOrderLineCreateDTO.builder()
					.orderQuantity(1)
					.build()
			))
			.build();
		mockMvc.perform(post(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}
	@Test
	void testCreateBeerOrderWrongOrderQuantity() throws Exception {
		Customer customer = customerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();
		Beer beer = beerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();

		BeerOrderCreateDTO dto = BeerOrderCreateDTO.builder()
			.customerId(customer.getId())
			.beerOrderLines(Set.of(
				BeerOrderLineCreateDTO.builder()
					.beerId(beer.getId())
					.orderQuantity(-12)
					.build()
			))
			.build();
		mockMvc.perform(post(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	void testCreateBeerOrderCustomerNotFound() throws Exception {
		BeerOrderCreateDTO dto = BeerOrderCreateDTO.builder()
			.customerId(UUID.randomUUID())
			.beerOrderLines(Set.of(
				BeerOrderLineCreateDTO.builder()
					.beerId(UUID.randomUUID())
					.orderQuantity(1)
					.build()
			))
			.build();
		mockMvc.perform(post(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isNotFound());
	}
	@Test
	void testCreateBeerOrderBeerNotFound() throws Exception {
		Customer customer = customerRepository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();

		BeerOrderCreateDTO dto = BeerOrderCreateDTO.builder()
			.customerId(customer.getId())
			.beerOrderLines(Set.of(
				BeerOrderLineCreateDTO.builder()
					.beerId(UUID.randomUUID())
					.orderQuantity(1)
					.build()
			))
			.build();
		mockMvc.perform(post(BeerOrderController.PATH)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	void testUpdateBeerOrder() throws Exception {
		BeerOrder beerOrder = repository.findAll(Pageable.ofSize(1).withPage(1)).getContent().getFirst();

		Set<BeerOrderLineUpdateDTO> lines = beerOrder.getBeerOrderLines()
			.stream()
			.map(bol -> BeerOrderLineUpdateDTO.builder()
				.id(bol.getId())
				.beerId(bol.getBeer().getId())
				.orderQuantity(bol.getOrderQuantity())
				.quantityAllocated(bol.getQuantityAllocated())
				.build())
			.collect(Collectors.toSet());
		BeerOrderUpdateDTO dto = BeerOrderUpdateDTO.builder()
			.customerId(beerOrder.getCustomer().getId())
			.customerRef("TestRef")
			.beerOrderLines(lines)
			.beerOrderShipment(BeerOrderShipmentUpdateDTO.builder()
				.trackingNumber("1234567890")
				.build())
			.build();


		mockMvc.perform(put(BeerOrderController.PATH_ID, beerOrder.getId())
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.customerRef", is("TestRef")));
	}
}
