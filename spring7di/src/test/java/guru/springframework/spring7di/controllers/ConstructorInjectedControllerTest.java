package guru.springframework.spring7di.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import guru.springframework.spring7di.services.impl.GreetingServiceImpl;

public class ConstructorInjectedControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(ConstructorInjectedControllerTest.class);
	ConstructorInjectedController constructorInjectedController;

	@BeforeEach
	void setUp() {
		constructorInjectedController = new ConstructorInjectedController(new GreetingServiceImpl());
	}

	@Test
	void testSayHello() {
		LOG.info("{}", constructorInjectedController.sayHello());
	}

}
