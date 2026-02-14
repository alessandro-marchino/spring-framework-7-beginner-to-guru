package guru.springframework.spring7webclient.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7webclient.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.JsonNode;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerClientImplTest {
	@Autowired BeerClient client;

	AtomicBoolean latch;

	@BeforeEach
	void setUp() {
		latch = new AtomicBoolean();
	}

    @Test
	@Order(10)
    void testListBeer() {
		client.listBeer()
			.doOnComplete(() -> latch.set(true))
			.subscribe(response -> {
				log.warn("Response: {}", response);
				assertThat(response).isInstanceOf(String.class);
				assertThat(response).isNotEmpty();
			});
		await().untilTrue(latch);
    }

	@Test
	@Order(11)
    void testListBeerMap() {
		client.listBeerMap()
			.doOnComplete(() -> latch.set(true))
			.collect(Collectors.toList())
			.subscribe(list -> {
				assertThat(list).hasSizeGreaterThanOrEqualTo(3);
				assertThat(list).allMatch(jn -> jn instanceof Map);
			});
		await().untilTrue(latch);
    }

	@Test
	@Order(12)
    void testListBeerJsonNode() {
		client.listBeerJsonNode()
			.doOnComplete(() -> latch.set(true))
			.collect(Collectors.toList())
			.subscribe(list -> {
				assertThat(list).hasSizeGreaterThanOrEqualTo(3);
				assertThat(list).allMatch(jn -> jn instanceof JsonNode);
			});
		await().untilTrue(latch);
    }

	@Test
	@Order(13)
    void testListBeerDto() {
		client.listBeerDto()
			.doOnComplete(() -> latch.set(true))
			.collect(Collectors.toList())
			.subscribe(list -> {
				assertThat(list).hasSizeGreaterThanOrEqualTo(3);
				assertThat(list).allMatch(jn -> jn instanceof BeerDTO);
			});
		await().untilTrue(latch);
    }

	@Test
	@Order(20)
    void testGetBeerById() {
		client.listBeerDto()
			.take(1L)
			.flatMap(dto -> client.getBeerById(dto.getId()))
			.doOnComplete(() -> latch.set(true))
			.subscribe(dto -> log.warn("Response: {} - {}", dto.getId(), dto.getBeerName()));

		await().untilTrue(latch);
    }

	@Test
	@Order(30)
    void testGetBeersByBeerStyle() {
		client.getBeersByBeerStyle("IPA")
			.doOnComplete(() -> latch.set(true))
			.subscribe(dto -> log.warn("Response: {} - {}", dto.getId(), dto.getBeerName()));

		await().untilTrue(latch);
    }

	@Test
	@Order(40)
    void testCreateBeer() {
		BeerDTO dto = BeerDTO.builder()
			.beerName("Mongo Bobs - TEST")
			.beerStyle("WEISS")
			.quantityOnHand(500)
			.price(new BigDecimal("9.99"))
			.upc("123456")
			.build();
		client.createBeer(dto)
			.doOnTerminate(() -> latch.set(true))
			.subscribe(beer -> log.warn("Response: {}", beer));

		await().untilTrue(latch);
    }

	@Test
	@Order(50)
    void testUpdateBeerById() {
		BeerDTO dto = client.getBeersByBeerStyle("WEISS").blockFirst();
		dto.setBeerName("Mongo Bobs - TEST updated");
		client.updateBeerById(dto.getId(), dto)
			.doOnTerminate(() -> latch.set(true))
			.subscribe(beer -> log.warn("Response: {}", beer));

		await().untilTrue(latch);
    }

	@Test
	@Order(60)
    void testPatchBeerById() {
		BeerDTO dto = client.getBeersByBeerStyle("WEISS").blockFirst();
		client.patchBeerById(dto.getId(), BeerDTO.builder().price(new BigDecimal("19.99")).build())
			.doOnTerminate(() -> latch.set(true))
			.subscribe(beer -> log.warn("Response: {}", beer));

		await().untilTrue(latch);
    }

	@Test
	@Order(70)
    void testDeleteBeerById() {
		BeerDTO dto = client.getBeersByBeerStyle("WEISS").blockFirst();
		client.deleteBeerById(dto.getId())
			.doOnSuccess(_ -> latch.set(true))
			.subscribe();

		await().untilTrue(latch);
    }
}
