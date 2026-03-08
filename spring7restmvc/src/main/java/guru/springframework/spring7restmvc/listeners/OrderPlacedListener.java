package guru.springframework.spring7restmvc.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.config.KafkaConfig;
import guru.springframework.spring7restmvcapi.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPlacedListener {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@EventListener
	@Async
	public void listen(OrderPlacedEvent event) {
		log.info("Order placed event listener");
		// Send to Kafka
		kafkaTemplate.send(KafkaConfig.ORDER_PLACED_TOPIC, event);
	}
}
