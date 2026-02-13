package guru.springframework.spring7reactivemongo.web.fn;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
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
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

import guru.springframework.spring7reactivemongo.mapper.BeerMapper;
import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.repositories.BeerRepository;
import reactor.core.publisher.Mono;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeerEndpointTest {

	@Container
	@ServiceConnection
	public static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest");

	@Autowired WebTestClient webTestClient;
	@Autowired BeerRepository beerRepository;
	@Autowired BeerMapper beerMapper;

	@Test
	@Order(30)
	void testCreateNewBeer() {
		webTestClient.post()
			.uri(BeerRouterConfig.PATH)
			.body(Mono.just(getTestBeer()), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().exists("location");
	}

	@Test
	@Order(35)
	@Disabled
	void testCreateNewBeerBadData() {
		BeerDTO testBeer = getTestBeer();
		testBeer.setBeerName("");

		webTestClient.post()
			.uri(BeerRouterConfig.PATH)
			.body(Mono.just(testBeer), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Test
	@Order(60)
	void testDeleteBeer() {
		BeerDTO dto = getSavedBeer();
		webTestClient.delete()
			.uri(BeerRouterConfig.PATH_ID, dto.getId())
			.exchange()
			.expectStatus().isNoContent();
	}

	@Test
	@Disabled
	void testDeleteBeerNotFound() {
		webTestClient.delete()
			.uri(BeerRouterConfig.PATH_ID, 999)
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	@Order(20)
	void testGetBeerById() {
		BeerDTO dto = getSavedBeer();
		webTestClient.get()
			.uri(BeerRouterConfig.PATH_ID, dto.getId())
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody(BeerDTO.class).value(beerDto -> {
				assertThat(beerDto.getId()).isEqualTo(dto.getId());
				assertThat(beerDto.getBeerName()).isEqualTo(dto.getBeerName());
			});
	}

	@Test
	@Disabled
	void testGetBeerByIdNotFound() {
		webTestClient.get()
			.uri(BeerRouterConfig.PATH_ID, 1)
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	@Order(10)
	void testListBeers() {
		webTestClient.get()
			.uri(BeerRouterConfig.PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(3);
	}

	@Test
	@Order(50)
	void testPatchExistingBeer() {
		BeerDTO dto = getSavedBeer();
		webTestClient.patch()
			.uri(BeerRouterConfig.PATH_ID, dto.getId())
			.body(Mono.just(getTestBeer()), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
	}

	@Test
	@Disabled
	void testPatchExistingBeerNotFound() {
		webTestClient.patch()
			.uri(BeerRouterConfig.PATH_ID, 999)
			.body(Mono.just(getTestBeer()), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	@Order(40)
	void testUpdateExistingBeer() {
		BeerDTO dto = getSavedBeer();
		webTestClient.put()
			.uri(BeerRouterConfig.PATH_ID, dto.getId())
			.body(Mono.just(getTestBeer()), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
	}

	@Test
	@Order(45)
	@Disabled
	void testUpdateExistingBeerBadData() {
		BeerDTO testBeer = getTestBeer();
		testBeer.setBeerName("");

		webTestClient.put()
			.uri(BeerRouterConfig.PATH_ID, 1)
			.body(Mono.just(testBeer), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Test
	@Disabled
	void testUpdateExistingBeerNotFound() {
		webTestClient.put()
			.uri(BeerRouterConfig.PATH_ID, 999)
			.body(Mono.just(getTestBeer()), BeerDTO.class)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNotFound();
	}

	BeerDTO getTestBeer() {
		return BeerDTO.builder()
				.beerName("Space Dust")
				.beerStyle("IPA")
				.price(BigDecimal.TEN)
				.quantityOnHand(12)
				.upc("123123")
				.build();
	}

	BeerDTO getSavedBeer() {
		return beerMapper.beerToBeerDTO(beerRepository.findAll().blockFirst());
	}
}
