package guru.springframework.spring7di.controllers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConstructorInjectedControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(ConstructorInjectedControllerTest.class);

	@Autowired
	ConstructorInjectedController constructorInjectedController;

	@Test
	void testSayHello() {
		LOG.info("{}", constructorInjectedController.sayHello());
	}

}
