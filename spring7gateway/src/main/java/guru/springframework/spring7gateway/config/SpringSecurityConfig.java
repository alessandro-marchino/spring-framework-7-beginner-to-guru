package guru.springframework.spring7gateway.config;

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
public class SpringSecurityConfig {

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
	SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		return http
			.csrf(spec -> spec.disable())
			.authorizeExchange(spec -> spec.anyExchange().authenticated())
			.oauth2ResourceServer(spec -> spec.jwt(Customizer.withDefaults()))
			.build();
	}
}
