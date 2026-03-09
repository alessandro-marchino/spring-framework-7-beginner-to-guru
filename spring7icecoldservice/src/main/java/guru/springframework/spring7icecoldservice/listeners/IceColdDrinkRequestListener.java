package guru.springframework.spring7icecoldservice.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.springframework.spring7icecoldservice.config.KafkaConfig;
import guru.springframework.spring7icecoldservice.service.DrinkRequestProcessor;
import guru.springframework.spring7restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring7restmvcapi.events.DrinkRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class IceColdDrinkRequestListener {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final DrinkRequestProcessor drinkRequestProcessor;

	@KafkaListener(groupId = "IceColdListener", topics = KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC)
	public void listen(DrinkRequestEvent event) {
		log.info("Received Message for ICE COLD: {}", event);
		drinkRequestProcessor.processDrinkRequest(event);
		kafkaTemplate.send(KafkaConfig.DRINK_PREPARED_TOPIC, DrinkPreparedEvent.builder()
			.berrOrderLine(event.getBeerOrderLine())
			.build());
	}
}
