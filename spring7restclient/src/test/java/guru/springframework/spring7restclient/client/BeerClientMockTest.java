package guru.springframework.spring7restclient.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7restclient.config.OAuthClientInterceptor;
import guru.springframework.spring7restclient.config.RestTemplateBuilderConfig;
import guru.springframework.spring7restclient.model.BeerDTO;
import guru.springframework.spring7restclient.model.BeerStyle;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest
@ExtendWith(MockitoExtension.class)
@Import({ RestTemplateBuilderConfig.class, BeerClientMockTest.TestConfig.class })
public class BeerClientMockTest {

	private static final String URL = "http://localhost:8081";
	private static final String BEARER = "Bearer test";

	@Autowired JsonMapper jsonMapper;
	@Autowired RestTemplateBuilder restTemplateBuilderConfigured;
	MockRestServiceServer server;

	@Mock RestTemplateBuilder mockRestTemplateBuilder;
	@InjectMocks BeerClientImpl beerClient;

	@Autowired ClientRegistrationRepository clientRegistrationRepository;
	@MockitoBean OAuth2AuthorizedClientManager manager;

	BeerDTO dto;
	String payload;

	@TestConfiguration
	@Import(RestTemplateBuilderConfig.class)
	public static class TestConfig {

		@Bean
		InMemoryClientRegistrationRepository clientRegistrationRepository() {
			return new InMemoryClientRegistrationRepository(ClientRegistration
				.withRegistrationId("springauth")
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.clientId("test")
				.tokenUri("test")
				.build());
		}

		@Bean
		InMemoryOAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
			return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
		}

		@Bean
		OAuthClientInterceptor oAuthClientInterceptor(OAuth2AuthorizedClientManager manager, ClientRegistrationRepository clientRegistrationRepository) {
			return new OAuthClientInterceptor(manager, clientRegistrationRepository);
		}
	}

	@BeforeEach
	void setUp() {
		ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("springauth");
		OAuth2AccessToken token = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "test", Instant.MIN, Instant.MAX);
		when(manager.authorize(any())).thenReturn(new OAuth2AuthorizedClient(clientRegistration, "test", token));

		RestTemplate restTemplate = restTemplateBuilderConfigured.build();
		server = MockRestServiceServer.bindTo(restTemplate).build();
		when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);

		// NOTE: RestClient is built using the mockRestTemplateBuilder.
		beerClient = new BeerClientImpl(RestClient.builder(mockRestTemplateBuilder.build()));
		dto = getBeerDto();
		payload = jsonMapper.writeValueAsString(dto);
	}

	@Test
	void testListBeersWithQueryParam() {
		String response = jsonMapper.writeValueAsString(getPage());
		URI uri = UriComponentsBuilder.fromUriString(URL + BeerClientImpl.GET_BEER_PATH)
			.queryParam("beerName", "ALE")
			.build()
			.toUri();

		server.expect(method(HttpMethod.GET))
			.andExpect(requestTo(uri))
			.andExpect(header("Authorization", BEARER))
			.andExpect(queryParam("beerName", "ALE"))
			.andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

		Page<BeerDTO> responsePage = beerClient.listBeers("ALE", null, null, null, null);
		assertThat(responsePage.getContent().size()).isEqualTo(1);
	}

	@Test
	void testDeleteNotFound() {
		server.expect(method(HttpMethod.DELETE))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
			.andExpect(header("Authorization", BEARER))
			.andRespond(withResourceNotFound());

		assertThrows(HttpClientErrorException.class, () -> beerClient.deleteBeer(dto.getId()));
		server.verify();
	}

	@Test
	void testDeleteBeer() {
		server.expect(method(HttpMethod.DELETE))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
			.andExpect(header("Authorization", BEARER))
			.andRespond(withNoContent());

		beerClient.deleteBeer(dto.getId());
		server.verify();
	}

	@Test
	void testUpdateBeer() {
		server.expect(method(HttpMethod.PUT))
				.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH,
						dto.getId()))
				.andExpect(header("Authorization", BEARER))
				.andRespond(withNoContent());

		mockGetOperation();

		BeerDTO responseDto = beerClient.updateBeer(dto);
		assertThat(responseDto.getId()).isEqualTo(dto.getId());
	}

	@Test
	void testCreateBeer() {
		URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.GET_BEER_BY_ID_PATH).build(dto.getId());

		server.expect(method(HttpMethod.POST))
			.andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
			.andExpect(header("Authorization", BEARER))
			.andRespond(withAccepted().location(uri));

		mockGetOperation();

		BeerDTO responseDto = beerClient.createBeer(dto);
		assertThat(responseDto.getId()).isEqualTo(dto.getId());
	}

	@Test
	void testGetById() {
		mockGetOperation();

		BeerDTO responseDto = beerClient.getBeerById(dto.getId());
		assertThat(responseDto.getId()).isEqualTo(dto.getId());
	}

	private void mockGetOperation() {
		server.expect(method(HttpMethod.GET))
			.andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
			.andExpect(header("Authorization", BEARER))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));
	}

	@Test
	void testListBeers() {
		String payload = jsonMapper.writeValueAsString(getPage());

		server.expect(method(HttpMethod.GET))
			.andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
			.andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

		Page<BeerDTO> dtos = beerClient.listBeers();
		assertThat(dtos.getContent().size()).isGreaterThan(0);
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
