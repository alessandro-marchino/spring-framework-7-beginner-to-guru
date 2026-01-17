package guru.springframework.spring7di.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertyInjectedControllerTest {

	@Autowired
	PropertyInjectedController propertyInjectedController;

	@Test
	void testSayHello() {
		assertEquals("Friends don't let friends do property injection!", propertyInjectedController.sayHello());
	}
}
