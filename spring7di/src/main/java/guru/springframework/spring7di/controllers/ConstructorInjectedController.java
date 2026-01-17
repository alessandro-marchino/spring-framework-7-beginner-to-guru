package guru.springframework.spring7di.controllers;

import org.springframework.stereotype.Controller;

import guru.springframework.spring7di.services.GreetingService;

@Controller
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
