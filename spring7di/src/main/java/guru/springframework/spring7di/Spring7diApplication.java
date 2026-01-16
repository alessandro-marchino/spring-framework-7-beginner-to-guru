package guru.springframework.spring7di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import guru.springframework.spring7di.controllers.MyController;

@SpringBootApplication
public class Spring7diApplication {
	private static final Logger LOG = LoggerFactory.getLogger(Spring7diApplication.class);

	public static void main(String[] args) {
		ApplicationContext appCtx = SpringApplication.run(Spring7diApplication.class, args);

		MyController controller = appCtx.getBean(MyController.class);
		LOG.info("In Main Method");
		LOG.info(controller.sayHello());
	}

}
