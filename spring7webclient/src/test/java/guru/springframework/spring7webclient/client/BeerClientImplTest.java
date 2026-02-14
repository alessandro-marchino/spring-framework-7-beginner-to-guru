package guru.springframework.spring7webclient.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7webclient.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;
import tools.jackson.databind.JsonNode;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerClientImplTest {
	@Autowired BeerClient client;

    @Test
	@Order(10)
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
	@Order(11)
    void testListBeerMap() {
		StepVerifier.create(client.listBeerMap())
			.recordWith(ArrayList::new)
			.expectNextCount(3)
			.consumeRecordedWith(list -> assertThat(list).hasSizeGreaterThanOrEqualTo(3))
			.verifyComplete();
    }

	@Test
	@Order(12)
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
	@Order(13)
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

	@Test
	@Order(20)
    void testGetBeerById() {
		AtomicBoolean latch = new AtomicBoolean();

		client.listBeerDto()
			.take(1L)
			.flatMap(dto -> client.getBeerById(dto.getId()))
			.doOnComplete(() -> latch.set(true))
			.subscribe(dto -> log.warn("Response: {} - {}", dto.getId(), dto.getBeerName()));

		await().untilTrue(latch);
    }
}
