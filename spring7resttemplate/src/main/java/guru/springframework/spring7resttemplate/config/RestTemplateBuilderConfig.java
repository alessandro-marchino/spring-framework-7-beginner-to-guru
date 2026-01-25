package guru.springframework.spring7resttemplate.config;

import java.time.Duration;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.autoconfigure.RestTemplateBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateBuilderConfig {

	@Bean
	RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
		DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory("http://localhost:8080");

		return configurer.configure(new RestTemplateBuilder())
			.uriTemplateHandler(defaultUriBuilderFactory)
			.connectTimeout(Duration.ofSeconds(5))
			.readTimeout(Duration.ofSeconds(2));
	}
}
