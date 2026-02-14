package guru.springframework.spring7webclient.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7webclient.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;

@SpringBootTest
@Slf4j
class BeerClientImplTest {
	@Autowired BeerClient client;

    @Test
    void testListBeer() {
		StepVerifier.create(client.listBeer())
			.consumeNextWith(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isInstanceOf(String.class);
				assertThat(response).isNotEmpty();
			})
			.verifyComplete();
    }

	@Test
    void testListBeerMap() {
		StepVerifier.create(client.listBeerMap())
			.consumeNextWith(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isInstanceOf(Map.class);
				assertThat(response).isNotEmpty();
			})
			.expectNextCount(2)
			.verifyComplete();
    }

	@Test
    void testListBeerJsonNode() {
		StepVerifier.create(client.listBeerJsonNode())
			.consumeNextWith(response -> {
				log.warn("Response: {}", response.toPrettyString());
				log.warn("Response Class: {}", response.getClass());
				assertThat(response).isInstanceOf(JsonNode.class);
			})
			.expectNextCount(2)
			.verifyComplete();
    }

	@Test
    void testListBeerDto() {
		StepVerifier.create(client.listBeerDto())
			.consumeNextWith(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isInstanceOf(BeerDTO.class);
			})
			.expectNextCount(2)
			.verifyComplete();
    }
}
