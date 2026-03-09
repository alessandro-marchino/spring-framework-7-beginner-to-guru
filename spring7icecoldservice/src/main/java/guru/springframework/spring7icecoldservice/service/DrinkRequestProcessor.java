package guru.springframework.spring7icecoldservice.service;

import guru.springframework.spring7restmvcapi.events.DrinkRequestEvent;

public interface DrinkRequestProcessor {
	void processDrinkRequest(DrinkRequestEvent event);
}
