package guru.springframework.spring7restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

	@Bean
	DefaultSecurityFilterChain filterChain(HttpSecurity http) {
		return http
			.authorizeHttpRequests(authoizizeCustomizer -> authoizizeCustomizer.anyRequest().authenticated())
			.csrf(csrfCustomizer -> csrfCustomizer.ignoringRequestMatchers("/api/**"))
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
			.build();
	}
}
