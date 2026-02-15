package guru.springframework.spring7webclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	private final String rootUrl;

	public WebClientConfig(@Value("${webclient.rooturl}") String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Bean
	WebClient webClient(WebClient.Builder builder) {
		return builder
			.baseUrl(rootUrl)
			.build();
	}
}
