package guru.springframework.spring7di.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service("propertyGreetingService")
public class GreetingServicePropertyInjected implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Friends don't let friends do property injection!";
	}
}
