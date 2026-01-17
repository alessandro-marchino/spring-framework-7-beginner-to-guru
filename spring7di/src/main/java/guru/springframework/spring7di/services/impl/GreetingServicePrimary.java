package guru.springframework.spring7di.services.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service
@Primary
public class GreetingServicePrimary implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Hello from the Primary bean!!";
	}
}
