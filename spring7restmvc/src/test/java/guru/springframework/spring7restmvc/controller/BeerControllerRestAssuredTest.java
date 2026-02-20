package guru.springframework.spring7restmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeerControllerRestAssuredTest {

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
				.get("/apiv1/beer")
			.then()
				.assertThat().statusCode(200);
	}
}
