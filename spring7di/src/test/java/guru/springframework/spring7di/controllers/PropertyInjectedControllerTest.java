package guru.springframework.spring7di.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import guru.springframework.spring7di.services.impl.GreetingServiceImpl;

public class PropertyInjectedControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyInjectedControllerTest.class);
	PropertyInjectedController propertyInjectedController;

	@BeforeEach
	void setUp() {
		propertyInjectedController = new PropertyInjectedController();
		propertyInjectedController.greetingService = new GreetingServiceImpl();
	}

	@Test
	void testSayHello() {
		LOG.info("{}", propertyInjectedController.sayHello());
	}
}
