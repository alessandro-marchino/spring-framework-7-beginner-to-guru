package guru.springframework.spring7di.controllers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(MyControllerTest.class);

    @Test
    void testSayHello() {
			MyController myController = new MyController();
			LOG.info("{}", myController.sayHello());
    }
}
