package guru.springframework.spring7webclient.client;

import java.util.Map;

import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

public interface BeerClient {

	Flux<String> listBeer();
	Flux<Map<String, Object>> listBeerMap();
	Flux<JsonNode> listBeerJsonNode();
}
