package guru.springframework.spring7resttemplate.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import guru.springframework.spring7resttemplate.client.impl.BeerClientImpl;
import guru.springframework.spring7resttemplate.config.RestTemplateBuilderConfig;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

@RestClientTest
@ExtendWith(MockitoExtension.class)
@Import(RestTemplateBuilderConfig.class)
public class BeerClientMockTest {
	static final String URL = "http://localhost:8080";

	@Autowired JsonMapper jsonMapper;
	@Autowired RestTemplateBuilder restTemplateBuilder;
	MockRestServiceServer server;
	BeerClient beerClient;

	@Mock RestTemplateBuilder mockRestTemplateBuilder;

	@BeforeEach
	void setUp() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);
		beerClient = new BeerClientImpl(mockRestTemplateBuilder);
	}

	@Test
	void testListBeers() {
		String payload = jsonMapper.writeValueAsString(getPage());

		server.expect(method(HttpMethod.GET))
			.andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
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
	PagedModel<BeerDTO> getPage() {
		Page<BeerDTO> page = new PageImpl<>(List.of(getBeerDto()), PageRequest.of(1, 25), 1);
		return new PagedModel<BeerDTO>(page);
	}
}
