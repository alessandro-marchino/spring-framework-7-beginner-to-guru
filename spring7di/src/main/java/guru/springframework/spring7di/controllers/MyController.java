package guru.springframework.spring7di.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {
	private static final Logger LOG = LoggerFactory.getLogger(MyController.class);

	public String sayHello() {
		LOG.info("I'm in the controller");
		return "Hello Everyone!!!";
	}
}
