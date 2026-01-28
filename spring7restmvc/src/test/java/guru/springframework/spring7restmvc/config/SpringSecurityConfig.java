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
			.csrf(csrfCustomizer -> csrfCustomizer.ignoringRequestMatchers("/api/**"))
			.httpBasic(Customizer.withDefaults())
			.build();
	}
}
