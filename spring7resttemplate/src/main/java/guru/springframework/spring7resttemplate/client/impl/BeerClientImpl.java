package guru.springframework.spring7resttemplate.client.impl;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7resttemplate.client.BeerClient;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import guru.springframework.spring7resttemplate.model.RestPageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerClientImpl implements BeerClient {

	private static final String GET_BEER_PATH = "/api/v1/beer";
	private static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

	private final RestTemplateBuilder restTemplateBuilder;

	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
		if(beerName != null) {
			uriComponentsBuilder.queryParam("beerName", beerName);
		}
		if(beerStyle != null) {
			uriComponentsBuilder.queryParam("beerStyle", beerStyle);
		}
		if(showInventory != null) {
			uriComponentsBuilder.queryParam("showInventory", showInventory);
		}
		if(pageNumber != null) {
			uriComponentsBuilder.queryParam("pageNumber", pageNumber);
		}
		if(pageSize != null) {
			uriComponentsBuilder.queryParam("pageSize", pageSize);
		}

		return doListBeers(uriComponentsBuilder);
	}

	private Page<BeerDTO> doListBeers(UriComponentsBuilder uriComponentsBuilder) {
		log.warn("URL: {}", uriComponentsBuilder.toUriString());

		RestTemplate restTemplate = restTemplateBuilder.build();
		ResponseEntity<RestPageImpl> jsonResponse = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), RestPageImpl.class);
		log.warn("Body: {}", jsonResponse.getBody());

		return jsonResponse.getBody();
	}

	@Override
	public Page<BeerDTO> listBeers() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
		return doListBeers(uriComponentsBuilder);
	}

	@Override
	public Page<BeerDTO> listBeers(Integer pageNumber, Integer pageSize) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
		if(pageNumber != null) {
			uriComponentsBuilder.queryParam("pageNumber", pageNumber);
		}
		if(pageSize != null) {
			uriComponentsBuilder.queryParam("pageSize", pageSize);
		}
		return doListBeers(uriComponentsBuilder);
	}

	@Override
	public BeerDTO getBeerById(UUID beerId, Boolean showInventory) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_BY_ID_PATH);
		if(showInventory != null) {
			uriComponentsBuilder.queryParam("showInventory", showInventory);
		}
		RestTemplate restTemplate = restTemplateBuilder.build();
		return restTemplate.getForObject(uriComponentsBuilder.buildAndExpand(beerId).toString(), BeerDTO.class);
	}

	@Override
	public BeerDTO createBeer(BeerDTO beerDTO) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		URI uri = restTemplate.postForLocation(GET_BEER_PATH, beerDTO);
		log.warn("URI returned: {}", uri);
		return restTemplate.getForObject(uri.getPath(), BeerDTO.class);
	}
}
