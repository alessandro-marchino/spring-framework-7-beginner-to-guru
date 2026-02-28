package guru.springframework.spring7restmvc.testutil;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import java.time.Instant;
import java.util.List;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {
	public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor JWT_REQUEST_POST_PROCESSOR = jwt()
		.jwt(jwt -> jwt
			.claims(claims -> claims
				.put("scope", "openid profile"))
			.subject("openid-client")
			.notBefore(Instant.now().minusSeconds(5)));

	@Configuration
	@EnableCaching
	public static class CacheConfig {
		@Bean
		SimpleCacheManager cacheManager() {
			SimpleCacheManager scm = new SimpleCacheManager();
			scm.setCaches(List.of(
				new NoOpCache("beerCache"),
				new NoOpCache("beerListCache"),
				new NoOpCache("customerCache"),
				new NoOpCache("customerListCache")
			));
			return scm;
		}
	}
}
