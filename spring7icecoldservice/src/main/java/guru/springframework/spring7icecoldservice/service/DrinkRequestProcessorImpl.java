package guru.springframework.spring7icecoldservice.service;

import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvcapi.events.DrinkRequestEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DrinkRequestProcessorImpl implements DrinkRequestProcessor {

	@Override
	public void processDrinkRequest(DrinkRequestEvent event) {
		log.info("Processing drink request...");
	}
}
