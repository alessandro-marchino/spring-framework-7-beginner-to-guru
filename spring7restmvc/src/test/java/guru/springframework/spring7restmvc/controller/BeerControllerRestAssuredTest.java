package guru.springframework.spring7restmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

@ActiveProfiles("test-restassured")
@Import(BeerControllerRestAssuredTest.TestConfig.class)
@ComponentScan("guru.springframework.spring7restmvc")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerControllerRestAssuredTest {

	@Configuration
	public static class TestConfig {
		@Bean
		DefaultSecurityFilterChain filterChain(HttpSecurity http) {
			return http
				.authorizeHttpRequests(spec -> spec.anyRequest().permitAll())
				.build();
		}
	}

	@LocalServerPort
	Integer localPort;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = localPort;
	}

	@Test
	void testListBeers() {
		given().contentType(ContentType.JSON)
			.when()
				.get("/api/v1/beer")
			.then()
				.assertThat().statusCode(200);
	}
}
