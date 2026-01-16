package guru.springframework.spring7di.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import guru.springframework.spring7di.services.GreetingService;
import guru.springframework.spring7di.services.impl.GreetingServiceImpl;

@Controller
public class MyController {
	private static final Logger LOG = LoggerFactory.getLogger(MyController.class);
	private final GreetingService greetingService;

	public MyController() {
		this.greetingService = new GreetingServiceImpl();
	}

	public String sayHello() {
		LOG.info("I'm in the controller");
		return greetingService.sayGreeting();
	}
}
