package guru.springframework.spring7reactive.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.spring7reactive.model.BeerDTO;

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
	@Autowired WebTestClient webTestClient;

    @Test
    void testCreateNewBeer() {

    }

    @Test
    void testDeleteBeer() {

    }

    @Test
    void testGetBeerById() {
		webTestClient.get().uri(BeerController.BEER_PATH + BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody(BeerDTO.class).value(beerDto -> {
				assertThat(beerDto.getId()).isEqualTo(1L);
			});
    }

    @Test
    void testListBeers() {
		webTestClient.get().uri(BeerController.BEER_PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    void testPatchExistingBeer() {

    }

    @Test
    void testUpdateExistingBeer() {

    }
}
