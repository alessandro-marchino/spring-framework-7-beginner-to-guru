package guru.springframework.spring7di.services.impl.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.GreetingService;

@Service("i18nService")
@Profile("ES")
public class SpanishGreetingService implements GreetingService {

	@Override
	public String sayGreeting() {
			return "Hola Mundo - ES";
	}
}
