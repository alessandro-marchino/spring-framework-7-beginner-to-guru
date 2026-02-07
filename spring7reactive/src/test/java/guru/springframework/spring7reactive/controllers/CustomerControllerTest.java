package guru.springframework.spring7reactive.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring7reactive.domain.Customer;
import guru.springframework.spring7reactive.model.CustomerDTO;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {
	@Autowired WebTestClient webTestClient;

    @Test
	@Order(30)
    void testCreateNewCustomer() {
		webTestClient.post()
				.uri(CustomerController.PATH)
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().location("http://localhost:8080" + CustomerController.PATH + "/4");
    }

    @Test
	@Order(60)
    void testDeleteCustomer() {
		webTestClient.delete()
				.uri(CustomerController.PATH + CustomerController.PATH_ID, 1)
			.exchange()
			.expectStatus().isNoContent();
    }

    @Test
	@Order(20)
    void testGetCustomerById() {
		webTestClient.get()
		.uri(CustomerController.PATH + CustomerController.PATH_ID, 1)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.expectBody(CustomerDTO.class).value(customerDto -> {
			assertThat(customerDto.getId()).isEqualTo(1L);
		});
    }

    @Test
	@Order(10)
    void testListCustomers() {
		webTestClient.get()
				.uri(CustomerController.PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
	@Order(50)
    void testPatchExistingCustomer() {
		webTestClient.patch()
				.uri(CustomerController.PATH + CustomerController.PATH_ID, 1)
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

    @Test
	@Order(40)
    void testUpdateExistingCustomer() {
		webTestClient.put()
				.uri(CustomerController.PATH + CustomerController.PATH_ID, 1)
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

	Customer getTestCustomer() {
		return Customer.builder()
			.customerName("Luke")
			.build();
	}

}
