package guru.springframework.spring7coolservice.listener;

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

import guru.springframework.spring7coolservice.config.KafkaConfig;
import guru.springframework.spring7restmvcapi.events.DrinkRequestEvent;
import guru.springframework.spring7restmvcapi.model.BeerDTO;
import guru.springframework.spring7restmvcapi.model.BeerOrderLineDTO;
import guru.springframework.spring7restmvcapi.model.BeerStyle;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = { KafkaConfig.DRINK_REQUEST_COOL_TOPIC, KafkaConfig.DRINK_PREPARED_TOPIC }, partitions = 1, brokerProperties = { "log.dir=target/embedded-kafka" } )
public class IceColdDrinkRequestListenerTest {
	@Autowired KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	@Autowired CoolDrinkRequestListener coolDrinkRequestListener;

	@Autowired DrinkPreparedKafkaListener drinkPreparedKafkaListener;

	@BeforeEach
	void setUp() {
		kafkaListenerEndpointRegistry.getListenerContainers().forEach(container -> {
			ContainerTestUtils.waitForAssignment(container, 1);
		});
	}

	@Test
	void listen() {
		DrinkRequestEvent drinkRequestEvent = DrinkRequestEvent.builder()
			.beerOrderLine(BeerOrderLineDTO.builder()
				.beer(BeerDTO.builder()
					.id(UUID.randomUUID())
					.beerStyle(BeerStyle.IPA)
					.beerName("Test Beer")
					.build())
				.build())
			.build();
		coolDrinkRequestListener.listen(drinkRequestEvent);
		await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
			assertThat(drinkPreparedKafkaListener.counter.get()).isEqualTo(1);
		});
	}
}
