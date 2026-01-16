package guru.springframework.spring7di.controllers;

import guru.springframework.spring7di.services.GreetingService;

public class SetterInjectedController {

	private GreetingService greetingService;

	/**
	 * @param greetingService the greetingService to set
	 */
	public void setGreetingService(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		return greetingService.sayGreeting();
	}
}
