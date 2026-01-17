package guru.springframework.spring7di.controllers.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("ES")
public class MyI18NControllerTestES {

	@Autowired MyI18NController controller;

	@Test
	void sayHello() {
		assertEquals("Hola Mundo - ES", controller.sayHello());
	}
}
