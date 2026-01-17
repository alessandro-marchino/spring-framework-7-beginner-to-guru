package guru.springframework.spring7di.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConstructorInjectedControllerTest {

	@Autowired
	ConstructorInjectedController constructorInjectedController;

	@Test
	void testSayHello() {
		String response = constructorInjectedController.sayHello();
		assertEquals("Hello Everyone from Base Service!!", response);
	}

}
