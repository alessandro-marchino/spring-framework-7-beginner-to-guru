package guru.springframework.spring7coldservice.service;

import guru.springframework.spring7restmvcapi.events.DrinkRequestEvent;

public interface DrinkRequestProcessor {
	void processDrinkRequest(DrinkRequestEvent event);
}
