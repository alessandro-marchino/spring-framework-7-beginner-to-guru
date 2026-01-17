package guru.springframework.spring7di.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service("setterGreetingBean")
public class GreetingServiceSetterInjection implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Hey I'm setting a Greeting!";
	}
}
