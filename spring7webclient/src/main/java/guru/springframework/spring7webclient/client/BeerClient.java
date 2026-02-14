package guru.springframework.spring7webclient.client;

import java.util.Map;

import guru.springframework.spring7webclient.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

public interface BeerClient {

	Flux<String> listBeer();
	Flux<Map<String, Object>> listBeerMap();
	Flux<JsonNode> listBeerJsonNode();
	Flux<BeerDTO> listBeerDto();
	Mono<BeerDTO> getBeerById(String id);
	Flux<BeerDTO> getBeersByBeerStyle(String beerStyle);

	Mono<BeerDTO> createBeer(BeerDTO dto);
	Mono<BeerDTO> updateBeerById(String id, BeerDTO dto);
	Mono<BeerDTO> patchBeerById(String id, BeerDTO dto);
	Mono<Void> deleteBeerById(String id);
}
