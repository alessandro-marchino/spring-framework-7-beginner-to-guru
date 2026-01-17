package guru.springframework.spring7di.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Hello Everyone from Base Service!!";
	}
}
