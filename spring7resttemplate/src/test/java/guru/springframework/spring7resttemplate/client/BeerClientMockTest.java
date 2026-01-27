package guru.springframework.spring7resttemplate.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import guru.springframework.spring7resttemplate.client.impl.BeerClientImpl;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

@RestClientTest(BeerClientImpl.class)
public class BeerClientMockTest {
	static final String URL = "http://localhost:8080";

	@Autowired BeerClient beerClient;
	@Autowired MockRestServiceServer server;
	@Autowired JsonMapper jsonMapper;

	@Test
	void testListBeers() {
		String payload = jsonMapper.writeValueAsString(getPage());

		server.expect(method(HttpMethod.GET))
			.andExpect(requestTo(BeerClientImpl.GET_BEER_PATH))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));


		Page<BeerDTO> dtos = beerClient.listBeers();
		assertThat(dtos).hasSizeGreaterThan(0);
	}
	BeerDTO getBeerDto() {
		return BeerDTO.builder()
			.id(UUID.randomUUID())
			.price(new BigDecimal("10.99"))
			.beerName("Mango Bobs")
			.beerStyle(BeerStyle.IPA)
			.quantityOnHand(500)
			.upc("123245")
			.build();
	}
	BeerDTOPageImpl getPage() {
		ObjectNode node = JsonNodeFactory.instance.objectNode()
			.put("number", 1)
			.put("size", 25)
			.put("totalElements", 1);
		return new BeerDTOPageImpl(List.of(getBeerDto()), node);
	}
}
