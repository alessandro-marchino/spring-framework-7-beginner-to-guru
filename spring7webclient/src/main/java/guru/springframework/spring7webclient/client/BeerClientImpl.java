package guru.springframework.spring7webclient.client;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import guru.springframework.spring7webclient.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {
	public static final String PATH = "/api/v3/beer";
	private final WebClient webClient;

	@Override
	public Flux<String> listBeer() {
		return webClient.get()
			.uri(PATH)
			.retrieve()
			.bodyToFlux(String.class);
	}

	@Override
	public Flux<Map<String, Object>> listBeerMap() {
		return webClient.get()
			.uri(PATH)
			.retrieve()
			.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {});
	}

	@Override
	public Flux<JsonNode> listBeerJsonNode() {
		return webClient.get()
			.uri(PATH)
			.retrieve()
			.bodyToFlux(JsonNode.class);
	}

	@Override
	public Flux<BeerDTO> listBeerDto() {
		return webClient.get()
			.uri(PATH)
			.retrieve()
			.bodyToFlux(BeerDTO.class);
	}
}
