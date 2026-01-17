package guru.springframework.spring7di.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MyControllerTest {

    @Test
    void testSayHello() {
			MyController myController = new MyController();
			assertEquals("Hello Everyone from Base Service!!", myController.sayHello());
    }
}
