package guru.springframework.spring7di.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.spring7di.services.GreetingService;

@Controller
public class SetterInjectedController {

	private GreetingService greetingService;

	/**
	 * @param greetingService the greetingService to set
	*/
	@Autowired
	public void setGreetingService(@Qualifier("setterGreetingBean") GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		return greetingService.sayGreeting();
	}
}
