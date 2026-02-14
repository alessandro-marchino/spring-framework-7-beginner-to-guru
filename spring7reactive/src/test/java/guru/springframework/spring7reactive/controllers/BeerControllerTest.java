package guru.springframework.spring7reactive.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

import java.math.BigDecimal;

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

import guru.springframework.spring7reactive.model.BeerDTO;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerControllerTest {
	@Autowired WebTestClient webTestClient;

	@Test
	@Order(5)
	void testUnauthorized() {
		webTestClient.get()
				.uri(BeerController.BEER_PATH)
			.exchange()
			.expectStatus().isUnauthorized();
	}

	@Test
	@Order(10)
    void testListBeers() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
				.uri(BeerController.BEER_PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(3);
    }

	@Test
	@Order(20)
    void testGetBeerById() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody(BeerDTO.class).value(beerDto -> {
				assertThat(beerDto.getId()).isEqualTo(1L);
			});
    }

	@Test
	@Order(25)
    void testGetBeerByIdNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
			.exchange()
			.expectStatus().isNotFound();
    }

    @Test
	@Order(30)
    void testCreateNewBeer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.post()
				.uri(BeerController.BEER_PATH)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().location("http://localhost:8080" + BeerController.BEER_PATH + "/4");
    }

	@Test
	@Order(35)
    void testCreateNewBeerBadData() {
		BeerDTO testBeer = getTestBeer();
		testBeer.setBeerName("");

		webTestClient
			.mutateWith(mockOAuth2Login())
			.post()
				.uri(BeerController.BEER_PATH)
				.body(Mono.just(testBeer), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isBadRequest();
    }

    @Test
	@Order(40)
    void testUpdateExistingBeer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

	@Test
	@Order(45)
    void testUpdateExistingBeerBadData() {
		BeerDTO testBeer = getTestBeer();
		testBeer.setBeerName("");

		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
				.body(Mono.just(testBeer), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isBadRequest();
    }

	@Test
	@Order(46)
    void testUpdateExistingBeerNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNotFound();
    }

    @Test
	@Order(50)
    void testPatchExistingBeer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.patch()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

	@Test
	@Order(55)
    void testPatchExistingBeerNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.patch()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNotFound();
    }

	@Test
	@Order(60)
    void testDeleteBeer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.delete()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isNoContent();
    }

	@Test
	@Order(65)
    void testDeleteBeerNotFound() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.delete()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 999)
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
}
