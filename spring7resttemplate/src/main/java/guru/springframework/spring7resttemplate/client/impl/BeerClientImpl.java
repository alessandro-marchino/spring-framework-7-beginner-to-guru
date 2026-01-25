package guru.springframework.spring7resttemplate.client.impl;

import java.net.URI;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.springframework.spring7resttemplate.client.BeerClient;
import guru.springframework.spring7resttemplate.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerClientImpl implements BeerClient {

	private final RestTemplateBuilder restTemplateBuilder;

	@Override
	public Page<BeerDTO> listBeers() {
		RestTemplate restTemplate = restTemplateBuilder.build();

		ResponseEntity<String> stringResponse = restTemplate.getForEntity(URI.create("http://localhost:8080/api/v1/beer"), String.class);
		log.warn("Body: {}", stringResponse.getBody());
		// TODO Auto-generated method stub
		return null;
	}
}
