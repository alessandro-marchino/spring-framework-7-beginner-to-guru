package guru.springframework.spring7reactive.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring7reactive.domain.Beer;
import guru.springframework.spring7reactive.model.BeerDTO;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
	@Autowired WebTestClient webTestClient;

    @Test
    void testCreateNewBeer() {
		webTestClient.post()
				.uri(BeerController.BEER_PATH)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().location("http://localhost:8080" + BeerController.BEER_PATH + "/4");
    }

    @Test
    void testDeleteBeer() {

    }

    @Test
    void testGetBeerById() {
		webTestClient.get()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody(BeerDTO.class).value(beerDto -> {
				assertThat(beerDto.getId()).isEqualTo(1L);
			});
    }

    @Test
    void testListBeers() {
		webTestClient.get()
				.uri(BeerController.BEER_PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    void testPatchExistingBeer() {
		webTestClient.patch()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

    @Test
    void testUpdateExistingBeer() {
		webTestClient.put()
				.uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
				.body(Mono.just(getTestBeer()), BeerDTO.class)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.exchange()
			.expectStatus().isNoContent();
    }

	Beer getTestBeer() {
		return Beer.builder()
			.beerName("Space Dust")
			.beerStyle("IPA")
			.price(BigDecimal.TEN)
			.quantityOnHand(12)
			.upc("123123")
			.build();
	}
}
