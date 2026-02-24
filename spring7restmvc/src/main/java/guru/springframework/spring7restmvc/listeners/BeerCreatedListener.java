package guru.springframework.spring7restmvc.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.events.BeerCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeerCreatedListener {

	@EventListener
	public void listen(BeerCreatedEvent event) {
		log.info("I heard a beer was created: {}", event.getBeer().getId());
	}
}
