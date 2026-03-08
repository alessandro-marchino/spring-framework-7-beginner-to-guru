package guru.springframework.spring7restmvc.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvcapi.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderPlacedListener {

	@EventListener
	@Async
	public void listen(OrderPlacedEvent event) {
		// Send to Kafka
		log.info("Order placed event listener");
	}
}
