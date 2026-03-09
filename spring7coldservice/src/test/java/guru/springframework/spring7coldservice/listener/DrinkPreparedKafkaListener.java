package guru.springframework.spring7coldservice.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import guru.springframework.spring7coldservice.config.KafkaConfig;
import guru.springframework.spring7restmvcapi.events.DrinkPreparedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DrinkPreparedKafkaListener {
	AtomicInteger counter = new AtomicInteger(0);

	@KafkaListener(groupId = "drink-prepared", topics = KafkaConfig.DRINK_PREPARED_TOPIC)
	public void receive(DrinkPreparedEvent event) {
		log.info("Received Message: {}", event);
		counter.incrementAndGet();
	}
}
