package guru.springframework.spring7resttemplate.client.impl;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.springframework.spring7resttemplate.client.BeerClient;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.RestPageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerClientImpl implements BeerClient {

	private static final String BASE_URL = "http://localhost:8080";
	private static final String GET_BEER_PATH = "/api/v1/beer";

	private final RestTemplateBuilder restTemplateBuilder;

	@SuppressWarnings("rawtypes")
	@Override
	public Page<BeerDTO> listBeers() {
		RestTemplate restTemplate = restTemplateBuilder.rootUri(BASE_URL).build();

		ResponseEntity<RestPageImpl> jsonResponse = restTemplate.getForEntity(GET_BEER_PATH, RestPageImpl.class);
		log.warn("Body: {}", jsonResponse.getBody());

		return jsonResponse.getBody();
	}
}
