package guru.springframework.spring7webclient.client;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import guru.springframework.spring7webclient.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {
	public static final String PATH = "/api/v3/beer";
	public static final String PATH_ID = PATH + "/{beerId}";
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

	@Override
	public Mono<BeerDTO> getBeerById(String id) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path(PATH_ID)
				.build(id))
			.retrieve()
			.bodyToMono(BeerDTO.class);
	}

	@Override
	public Flux<BeerDTO> getBeersByBeerStyle(String beerStyle) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path(PATH)
				.queryParam("beerStyle", beerStyle)
				.build())
			.retrieve()
			.bodyToFlux(BeerDTO.class);
	}

	@Override
	public Mono<BeerDTO> createBeer(BeerDTO dto) {
		return webClient.post()
			.uri(PATH)
			.body(Mono.just(dto), BeerDTO.class)
			.retrieve()
			.toBodilessEntity()
			.flatMap(re -> Mono.just(re.getHeaders().getLocation().toString()))
			.map(path -> path.split("/")[path.split("/").length - 1])
			.flatMap(this::getBeerById);
	}

	@Override
	public Mono<BeerDTO> updateBeerById(String id, BeerDTO dto) {
		return webClient.put()
			.uri(uriBuilder -> uriBuilder
				.path(PATH_ID)
				.build(id))
			.body(Mono.just(dto), BeerDTO.class)
			.retrieve()
			.toBodilessEntity()
			.flatMap(_ -> getBeerById(id));
	}

	@Override
	public Mono<BeerDTO> patchBeerById(String id, BeerDTO dto) {
		return webClient.patch()
			.uri(uriBuilder -> uriBuilder
				.path(PATH_ID)
				.build(id))
			.body(Mono.just(dto), BeerDTO.class)
			.retrieve()
			.toBodilessEntity()
			.flatMap(_ -> getBeerById(id));
	}

	@Override
	public Mono<Void> deleteBeerById(String id) {
		return webClient.delete()
			.uri(uriBuilder -> uriBuilder
				.path(PATH_ID)
				.build(id))
			.retrieve()
			.toBodilessEntity()
			.then();
	}
}
