package guru.springframework.spring7webclient.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
class BeerClientImplTest {
	@Autowired BeerClient client;

    @Test
    void testListBeer() {
		StepVerifier.create(client.listBeer())
			.consumeNextWith(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isNotEmpty();
			})
			.verifyComplete();
    }

	@Test
    void testListBeerMap() {
		StepVerifier.create(client.listBeerMap())
			.consumeNextWith(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isNotEmpty();
			})
			.expectNextCount(2)
			.verifyComplete();
    }
}
