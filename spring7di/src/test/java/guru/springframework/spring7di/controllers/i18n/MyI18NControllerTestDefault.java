package guru.springframework.spring7di.controllers.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyI18NControllerTestDefault {

	@Autowired MyI18NController controller;

	@Test
	void sayHello() {
		assertEquals("Hello World - EN", controller.sayHello());
	}
}
