package guru.springframework.spring7di;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import guru.springframework.spring7di.controllers.MyController;

@SpringBootTest
class Spring7diApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(Spring7diApplicationTests.class);

	@Autowired
	ApplicationContext appCtx;
	@Autowired
	MyController myController;

	@Test
	void contextLoads() {
	}

	@Test
	void testGetControllerFromCtx() {
		MyController controller = appCtx.getBean(MyController.class);
		assertNotNull(controller);
		LOG.info("In Test Method \"testGetControllerFromCtx\"");
		LOG.info(controller.sayHello());
	}

	@Test
	void testAutowireOfComponent() {
		assertNotNull(myController);
		LOG.info("In Test Method \"testAutowireOfComponent\"");
		LOG.info(myController.sayHello());
	}

}
