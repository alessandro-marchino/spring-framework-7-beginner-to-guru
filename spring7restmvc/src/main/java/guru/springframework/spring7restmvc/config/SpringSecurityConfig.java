package guru.springframework.spring7restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Profile("!test-restassured")
@Configuration
public class SpringSecurityConfig {

	@Bean
	DefaultSecurityFilterChain filterChain(HttpSecurity http) {
		return http
			.authorizeHttpRequests(spec ->
				spec
					.requestMatchers("/v3/api-docs**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
					.anyRequest().authenticated())
			.csrf(csrfCustomizer -> csrfCustomizer.ignoringRequestMatchers("/api/**"))
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
			.build();
	}
}
