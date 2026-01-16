package guru.springframework.spring7di.controllers;

import guru.springframework.spring7di.services.GreetingService;

public class ConstructorInjectedController {

	private final GreetingService greetingService;

	/**
	 * @param greetingService
	 */
	public ConstructorInjectedController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		return greetingService.sayGreeting();
	}
}
