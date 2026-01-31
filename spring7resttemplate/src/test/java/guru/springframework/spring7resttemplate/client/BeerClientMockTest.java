package guru.springframework.spring7resttemplate.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7resttemplate.client.impl.BeerClientImpl;
import guru.springframework.spring7resttemplate.config.OAuthClientInterceptor;
import guru.springframework.spring7resttemplate.config.RestTemplateBuilderConfig;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import tools.jackson.databind.json.JsonMapper;

@RestClientTest
@ExtendWith(MockitoExtension.class)
@Import({ RestTemplateBuilderConfig.class, BeerClientMockTest.TestConfig.class })
public class BeerClientMockTest {
	static final String URL = "http://localhost:8080";
	public static final String BEARER = "Bearer test";

	@Autowired JsonMapper jsonMapper;
	@Autowired RestTemplateBuilder restTemplateBuilder;
	MockRestServiceServer server;

	@Mock RestTemplateBuilder mockRestTemplateBuilder;
	@InjectMocks BeerClientImpl beerClient;

	@Autowired ClientRegistrationRepository clientRegistrationRepository;
	@MockitoBean OAuth2AuthorizedClientManager manager;

	BeerDTO beer;
	String payload;

	@TestConfiguration
	static class TestConfig {
		@Bean
		InMemoryClientRegistrationRepository clientRegistrationRepository() {
			System.out.println("clientRegistrationRepository - HERE");
			return new InMemoryClientRegistrationRepository(ClientRegistration
				.withRegistrationId("springauth")
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.tokenUri("http://localhost:9000")
				.clientId("test")
				.clientSecret("test")
				.build());
		}
		@Bean
		InMemoryOAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
			System.out.println("auth2AuthorizedClientService - HERE");
			return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
		}
		@Bean
		OAuthClientInterceptor oAuthClientInterceptor(OAuth2AuthorizedClientManager manager, ClientRegistrationRepository clientRegistrationRepository) {
			System.out.println("oAuthClientInterceptor - HERE");
			return new OAuthClientInterceptor(manager, clientRegistrationRepository);
		}
	}


	@BeforeEach
	void setUp() {
		ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("springauth");
		OAuth2AccessToken token = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "test", Instant.MIN, Instant.MAX);
		when(manager.authorize(any())).thenReturn((new OAuth2AuthorizedClient(clientRegistration, "test", token)));

		RestTemplate restTemplate = restTemplateBuilder.build();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);

		beer = getBeerDto();
		payload = jsonMapper.writeValueAsString(beer);
	}

	@AfterEach
	void tearDown() {
		server.verify();
	}

	@Test
	void testListBeers() {
		String payload = jsonMapper.writeValueAsString(getPage());

		server.expect(method(HttpMethod.GET))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));


		Page<BeerDTO> dtos = beerClient.listBeers();
		assertThat(dtos).hasSizeGreaterThan(0);
	}
	@Test
	void testListBeersWithQueryParam() {
		String payload = jsonMapper.writeValueAsString(getPage());
		URI uri = UriComponentsBuilder.fromUriString(URL + BeerClientImpl.GET_BEER_PATH)
			.queryParam("beerName", "ALE")
			.build()
			.toUri();

		server.expect(method(HttpMethod.GET))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestTo(uri))
			.andExpect(queryParam("beerName", "ALE"))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));


		Page<BeerDTO> dtos = beerClient.listBeers("ALE", null, null, null, null);
		assertThat(dtos).hasSizeGreaterThan(0);
	}
	@Test
	void testGetBeerById() {
		server.expect(method(HttpMethod.GET))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH + "?showInventory=true", beer.getId()))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

		BeerDTO dto = beerClient.getBeerById(beer.getId(), true);
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(beer.getId());
		assertThat(dto.getPrice()).isEqualTo(beer.getPrice());
		assertThat(dto.getBeerName()).isEqualTo(beer.getBeerName());
		assertThat(dto.getBeerStyle()).isEqualTo(beer.getBeerStyle());
		assertThat(dto.getQuantityOnHand()).isEqualTo(beer.getQuantityOnHand());
		assertThat(dto.getUpc()).isEqualTo(beer.getUpc());
	}

	@Test
	void testCreateBeer() {
		URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.GET_BEER_BY_ID_PATH).build(beer.getId());

		server.expect(method(HttpMethod.POST))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
			.andRespond(withAccepted().location(uri));

		server.expect(method(HttpMethod.GET))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, beer.getId()))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

		BeerDTO dto = beerClient.createBeer(beer);
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(beer.getId());
	}

	@Test
	void testUpdateBeer() {
		server.expect(method(HttpMethod.PUT))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, beer.getId()))
			.andRespond(withNoContent());

		server.expect(method(HttpMethod.GET))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH + "?showInventory=true", beer.getId()))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

		BeerDTO dto = beerClient.updateBeer(beer);
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(beer.getId());
	}

	@Test
	void testDeleteBeer() {
		server.expect(method(HttpMethod.DELETE))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, beer.getId()))
			.andRespond(withNoContent());

		beerClient.deleteBeer(beer.getId());
	}

	@Test
	void testDeleteBeerNotFound() {
		server.expect(method(HttpMethod.DELETE))
			.andExpect(header("Authorization", BEARER))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, beer.getId()))
			.andRespond(withResourceNotFound());

		assertThrows(HttpClientErrorException.class, () -> beerClient.deleteBeer(beer.getId()));
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
