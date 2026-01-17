package guru.springframework.spring7di.services.impl.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service("i18nService")
@Profile("!EN & !ES & !default")
public class PrimaryGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
			return "NULL";
	}
}
