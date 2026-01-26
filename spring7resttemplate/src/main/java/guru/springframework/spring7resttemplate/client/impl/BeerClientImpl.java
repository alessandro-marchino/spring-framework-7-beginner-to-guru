package guru.springframework.spring7resttemplate.client.impl;

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
}
