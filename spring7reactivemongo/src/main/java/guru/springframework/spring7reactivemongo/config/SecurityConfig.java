package guru.springframework.spring7reactivemongo.config;

import org.springframework.boot.security.autoconfigure.actuate.web.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	@Order(1)
	SecurityWebFilterChain actuatorFilterChain(ServerHttpSecurity http) {
		return http
			.securityMatcher(EndpointRequest.toAnyEndpoint())
			.authorizeExchange(spec -> spec.anyExchange().permitAll())
			.build();
	}

	@Bean
	@Order(2)
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().authenticated())
			.oauth2ResourceServer(oauth2ResourceServerSpec -> oauth2ResourceServerSpec.jwt(Customizer.withDefaults()))
			.csrf(csrfSpec -> csrfSpec.disable())
			.build();
	}
}
