package guru.springframework.spring7webclient.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

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
			.recordWith(ArrayList::new)
			.expectNextCount(3)
			.consumeRecordedWith(list -> assertThat(list).hasSizeGreaterThanOrEqualTo(3))
			.verifyComplete();
    }

	@Test
    void testListBeerJsonNode() {
		StepVerifier.create(client.listBeerJsonNode())
			.recordWith(ArrayList::new)
			.consumeNextWith(response -> {
				log.warn("Response: {}", response.toPrettyString());
				log.warn("Response Class: {}", response.getClass());
				assertThat(response).isInstanceOf(JsonNode.class);
			})
			.expectNextCount(2)
			.consumeRecordedWith(list -> assertThat(list).hasSizeGreaterThanOrEqualTo(3))
			.verifyComplete();
    }

	@Test
    void testListBeerDto() {
		StepVerifier.create(client.listBeerDto())
			.recordWith(ArrayList::new)
			.consumeNextWith(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isInstanceOf(BeerDTO.class);
			})
			.expectNextCount(2)
			.consumeRecordedWith(list -> assertThat(list).hasSizeGreaterThanOrEqualTo(3))
			.verifyComplete();
    }
}
