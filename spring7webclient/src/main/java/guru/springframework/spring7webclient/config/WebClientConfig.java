package guru.springframework.spring7webclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;

@Configuration
public class WebClientConfig {

	private final String rootUrl;
	private final ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager;

	public WebClientConfig(@Value("${webclient.rooturl}") String rootUrl, ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager) {
		this.rootUrl = rootUrl;
		this.reactiveOAuth2AuthorizedClientManager = reactiveOAuth2AuthorizedClientManager;
	}

	@Bean
	WebClient webClient(WebClient.Builder builder) {
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(reactiveOAuth2AuthorizedClientManager);
		oauth.setDefaultClientRegistrationId("springauth");
		LogbookExchangeFilterFunction logbookExchangeFilterFunction = new LogbookExchangeFilterFunction(Logbook.builder().build());

		return builder
			.baseUrl(rootUrl)
			.filter(oauth)
			.filter(logbookExchangeFilterFunction)
			.build();
	}
}
