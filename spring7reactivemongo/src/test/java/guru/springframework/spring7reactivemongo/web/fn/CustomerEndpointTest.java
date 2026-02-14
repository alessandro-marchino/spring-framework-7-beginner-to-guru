package guru.springframework.spring7reactivemongo.web.fn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

import guru.springframework.spring7reactivemongo.mapper.CustomerMapper;
import guru.springframework.spring7reactivemongo.model.CustomerDTO;
import guru.springframework.spring7reactivemongo.repositories.CustomerRepository;
import reactor.core.publisher.Mono;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerEndpointTest {

	@Container
	@ServiceConnection
	public static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest");

	@Autowired WebTestClient webTestClient;
	@Autowired CustomerRepository repository;
	@Autowired CustomerMapper mapper;

	@Test
	@Order(10)
    void testListCustomersUnauthorized() {
		webTestClient
			.get()
				.uri(CustomerRouterConfig.PATH)
			.exchange()
			.expectStatus().isUnauthorized();
    }

	@Test
	@Order(10)
    void testListCustomers() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
				.uri(CustomerRouterConfig.PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(3);
    }

	@Test
	@Order(15)
    void testListCustomersByName() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(UriComponentsBuilder
				.fromPath(CustomerRouterConfig.PATH)
				.queryParam("customerName", "Bob")
				.build()
				.toUri())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(1);
    }

	@Test
	@Order(20)
    void testGetCustomerById() {
		CustomerDTO dto = getSavedCustomer();
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(CustomerRouterConfig.PATH_ID, dto.getId())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody(CustomerDTO.class).value(customerDto -> {
				assertThat(customerDto.getId()).isEqualTo(dto.getId());
				assertThat(customerDto.getCustomerName()).isEqualTo(dto.getCustomerName());
			});
    }

	@Test
	@Order(25)
    void testGetCustomerByIdNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(CustomerRouterConfig.PATH_ID, 999)
			.exchange()
			.expectStatus().isNotFound();
    }

    @Test
	@Order(30)
    void testCreateNewCustomer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.post()
			.uri(CustomerRouterConfig.PATH)
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().exists("location");
    }

	@Test
	@Order(35)
    void testCreateNewCustomerBadRequest() {
		CustomerDTO customerDTO = getTestCustomer();
		customerDTO.setCustomerName("");
		webTestClient
			.mutateWith(mockOAuth2Login())
			.post()
			.uri(CustomerRouterConfig.PATH)
				.body(Mono.just(customerDTO), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isBadRequest();
    }

    @Test
	@Order(40)
    void testUpdateExistingCustomer() {
		CustomerDTO dto = getSavedCustomer();
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
			.uri(CustomerRouterConfig.PATH_ID, dto.getId())
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

	@Test
	@Order(44)
    void testUpdateExistingCustomerNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
			.uri(CustomerRouterConfig.PATH_ID, 999)
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNotFound();
    }

	@Test
	@Order(45)
    void testUpdateExistingCustomerBadRequest() {
		CustomerDTO dto = getSavedCustomer();
		dto.setCustomerName("");
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
			.uri(CustomerRouterConfig.PATH_ID, dto.getId())
				.body(Mono.just(dto), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isBadRequest();
    }

	@Test
	@Order(50)
    void testPatchExistingCustomer() {
		CustomerDTO dto = getSavedCustomer();
		webTestClient
			.mutateWith(mockOAuth2Login())
			.patch()
			.uri(CustomerRouterConfig.PATH_ID, dto.getId())
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

	@Test
	@Order(55)
    void testPatchExistingCustomerNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.patch()
			.uri(CustomerRouterConfig.PATH_ID, 999)
				.body(Mono.just(getTestCustomer()), CustomerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNotFound();
    }

	@Test
	@Order(60)
    void testDeleteCustomer() {
		CustomerDTO dto = getSavedCustomer();
		webTestClient
			.mutateWith(mockOAuth2Login())
			.delete()
			.uri(CustomerRouterConfig.PATH_ID, dto.getId())
			.exchange()
			.expectStatus().isNoContent();
    }

	@Test
	@Order(65)
    void testDeleteCustomerNotFOund() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.delete()
			.uri(CustomerRouterConfig.PATH_ID, 999)
			.exchange()
			.expectStatus().isNotFound();
    }

	CustomerDTO getSavedCustomer() {
		return mapper.customerToCustomerDTO(repository.findAll().blockFirst());
	}

	CustomerDTO getTestCustomer() {
		return CustomerDTO.builder()
			.customerName("Luke")
			.build();
	}
}
