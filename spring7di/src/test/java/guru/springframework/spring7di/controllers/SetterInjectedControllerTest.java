package guru.springframework.spring7di.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import guru.springframework.spring7di.services.impl.GreetingServiceImpl;

public class SetterInjectedControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(SetterInjectedControllerTest.class);
	SetterInjectedController setterInjectedController;

	@BeforeEach
	void setUp() {
		setterInjectedController = new SetterInjectedController();
		setterInjectedController.setGreetingService(new GreetingServiceImpl());
	}

	@Test
	void testSayHello() {
		LOG.info("{}", setterInjectedController.sayHello());
	}
}
