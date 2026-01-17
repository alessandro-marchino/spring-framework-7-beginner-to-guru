package guru.springframework.spring7di.services.impl.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service("i18nService")
@Profile({ "EN", "default" })
public class EnglishGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Hello World - EN";
	}
}
