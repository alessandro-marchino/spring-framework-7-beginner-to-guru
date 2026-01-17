package guru.springframework.spring7di.controllers.i18n;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.spring7di.services.GreetingService;

@Controller
public class MyI18NController {

	private final GreetingService greetingService;

	/**
	 * @param greetingService
	 */
	public MyI18NController(@Qualifier("i18nService") GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		return greetingService.sayGreeting();
	}
}
