package guru.springframework.spring7webclient.client;

import java.util.Map;

import guru.springframework.spring7webclient.model.BeerDTO;
import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

public interface BeerClient {

	Flux<String> listBeer();
	Flux<Map<String, Object>> listBeerMap();
	Flux<JsonNode> listBeerJsonNode();
	Flux<BeerDTO> listBeerDto();
}
