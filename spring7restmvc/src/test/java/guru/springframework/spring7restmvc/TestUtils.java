package guru.springframework.spring7restmvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import java.time.Instant;

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
}
