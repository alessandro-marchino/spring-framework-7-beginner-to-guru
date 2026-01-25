package guru.springframework.spring7resttemplate.client.impl;

import java.util.Map;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.springframework.spring7resttemplate.client.BeerClient;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.JsonNode;

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

		ResponseEntity<String> stringResponse = restTemplate.getForEntity(GET_BEER_PATH, String.class);
		log.warn("Body: {}", stringResponse.getBody());

		ResponseEntity<Map> mapResponse = restTemplate.getForEntity(GET_BEER_PATH, Map.class);
		log.warn("Body: {}", mapResponse.getBody());

		ResponseEntity<JsonNode> jsonResponse = restTemplate.getForEntity(GET_BEER_PATH, JsonNode.class);
		log.warn("Body: {}", jsonResponse.getBody());
		jsonResponse.getBody().findPath("content").iterator().forEachRemaining(node -> log.info("BeerName: {}", node.get("beerName").asString()));

		// TODO Auto-generated method stub
		return null;
	}
}
