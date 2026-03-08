package guru.springframework.spring7restmvc.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.config.KafkaConfig;
import guru.springframework.spring7restmvcapi.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderPlacedKafkaListener {
	AtomicInteger messageCounter = new AtomicInteger(0);

	@KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.ORDER_PLACED_TOPIC)
	public void receive(OrderPlacedEvent event) {
		log.info("Received Message: {}", event);
		messageCounter.incrementAndGet();
	}
}
