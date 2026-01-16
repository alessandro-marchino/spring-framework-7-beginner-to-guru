package guru.springframework.spring7di.services.impl;

import guru.springframework.spring7di.services.GreetingService;

public class GreetingServiceImpl implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Hello Everyone from Base Service!!";
	}
}
