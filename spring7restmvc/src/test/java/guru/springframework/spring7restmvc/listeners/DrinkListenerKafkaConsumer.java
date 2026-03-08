package guru.springframework.spring7restmvc.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DrinkListenerKafkaConsumer {
	AtomicInteger iceColdMessageCounter = new AtomicInteger(0);
	AtomicInteger coldMessageCounter = new AtomicInteger(0);
	AtomicInteger coolMessageCounter = new AtomicInteger(0);

	@KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC)
	public void listenIceCold() {
		log.warn("I am listening - ice cold");
		iceColdMessageCounter.incrementAndGet();
	}
	@KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.DRINK_REQUEST_COLD_TOPIC)
	public void listenCold() {
		log.warn("I am listening - cold");
		coldMessageCounter.incrementAndGet();
	}
	@KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.DRINK_REQUEST_COOL_TOPIC)
	public void listenCool() {
		log.warn("I am listening - cool");
		coolMessageCounter.incrementAndGet();
	}
}
