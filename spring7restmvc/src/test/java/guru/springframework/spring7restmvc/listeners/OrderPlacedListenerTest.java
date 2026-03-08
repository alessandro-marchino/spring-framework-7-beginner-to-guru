package guru.springframework.spring7restmvc.listeners;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

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
import guru.springframework.spring7restmvcapi.model.BeerOrderDTO;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = { KafkaConfig.ORDER_PLACED_TOPIC }, partitions = 1, brokerProperties = { "log.dir=target/embedded-kafka" } )
public class OrderPlacedListenerTest {
	@Autowired KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	@Autowired OrderPlacedListener orderPlacedListener;
	@Autowired OrderPlacedKafkaListener orderPlacedKafkaListener;

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
}
