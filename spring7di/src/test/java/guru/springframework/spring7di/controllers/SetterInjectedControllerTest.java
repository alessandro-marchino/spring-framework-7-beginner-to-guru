package guru.springframework.spring7di.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetterInjectedControllerTest {

	@Autowired
	SetterInjectedController setterInjectedController;

	@Test
	void testSayHello() {
		assertEquals("Hey I'm setting a Greeting!", setterInjectedController.sayHello());
	}
}
