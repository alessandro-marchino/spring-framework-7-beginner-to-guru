package guru.springframework.spring7restmvc.listeners;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.greaterThan;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import guru.springframework.spring7restmvc.config.KafkaConfig;
import guru.springframework.spring7restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring7restmvcapi.model.BeerDTO;
import guru.springframework.spring7restmvcapi.model.BeerOrderDTO;
import guru.springframework.spring7restmvcapi.model.BeerOrderLineDTO;
import guru.springframework.spring7restmvcapi.model.BeerStyle;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = { KafkaConfig.ORDER_PLACED_TOPIC }, partitions = 1, brokerProperties = { "log.dir=target/embedded-kafka" } )
public class OrderPlacedListenerTest {
	@Autowired KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	@Autowired OrderPlacedListener orderPlacedListener;
	@Autowired DrinkSplitterRouter drinkSplitterRouter;

	@Autowired OrderPlacedKafkaListener orderPlacedKafkaListener;
	@Autowired DrinkListenerKafkaConsumer drinkListenerKafkaListener;

	@BeforeEach
	void setUp() {
		kafkaListenerEndpointRegistry.getListenerContainers().forEach(container -> {
			ContainerTestUtils.waitForAssignment(container, 1);
		});
	}

	@Test
	void listen() {
		OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
			.beerOrderDTO(BeerOrderDTO.builder()
				.id(UUID.randomUUID())
				.build())
			.build();
		orderPlacedListener.listen(orderPlacedEvent);
		await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(orderPlacedKafkaListener.messageCounter.get()).isEqualTo(1);
		});
	}

	@Test
	void listenSplitter() {
		drinkSplitterRouter.receive(OrderPlacedEvent.builder()
			.beerOrderDTO(buildOrder())
			.build());
		await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS).until(drinkListenerKafkaListener.iceColdMessageCounter::get, greaterThan(0));
		await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS).until(drinkListenerKafkaListener.coldMessageCounter::get, greaterThan(0));
		await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS).until(drinkListenerKafkaListener.coolMessageCounter::get, greaterThan(0));
	}

	BeerOrderDTO buildOrder() {
		Set<BeerOrderLineDTO> beerOrderLines = new HashSet<>();

		beerOrderLines.add(BeerOrderLineDTO.builder()
			.beer(BeerDTO.builder()
				.id(UUID.randomUUID())
				.beerStyle(BeerStyle.IPA)
				.beerName("IPA Test Beer")
				.build())
			.build());
		beerOrderLines.add(BeerOrderLineDTO.builder()
			.beer(BeerDTO.builder()
				.id(UUID.randomUUID())
				.beerStyle(BeerStyle.LAGER)
				.beerName("LAGER Test Beer")
				.build())
			.build());
		beerOrderLines.add(BeerOrderLineDTO.builder()
			.beer(BeerDTO.builder()
				.id(UUID.randomUUID())
				.beerStyle(BeerStyle.GOSE)
				.beerName("GOSE Test Beer")
				.build())
			.build());
		return BeerOrderDTO.builder()
			.id(UUID.randomUUID())
			.beerOrderLines(beerOrderLines)
			.build();

	}
}
