package guru.springframework.spring7restmvc.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import guru.springframework.spring7restmvc.TestUtils;
import guru.springframework.spring7restmvc.entities.BeerOrder;
import guru.springframework.spring7restmvc.repositories.BeerOrderRepository;

@SpringBootTest
class BeerOrderControllerIT {
	@Autowired WebApplicationContext wac;
	@Autowired BeerOrderRepository repository;
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
			.andExpect(jsonPath("$.id", is(beerOrder.getId())))
			.andExpect(jsonPath("$.customer.id", is(beerOrder.getCustomer().getId())));
	}

	@Test
	void testGetBeerOrderByIdNotFound() throws Exception {
		mockMvc.perform(get(BeerOrderController.PATH_ID, 999)
				.with(TestUtils.JWT_REQUEST_POST_PROCESSOR))
			.andExpect(status().isNotFound());
	}
}
