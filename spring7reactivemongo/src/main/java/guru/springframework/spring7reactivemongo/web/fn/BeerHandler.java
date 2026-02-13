package guru.springframework.spring7reactivemongo.web.fn;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BeerHandler {
	private final BeerService beerService;

	public Mono<ServerResponse> listBeers(ServerRequest request) {
		return ServerResponse.ok()
			.body(beerService.listBeers(), BeerDTO.class);
	}

	public Mono<ServerResponse> getBeerById(ServerRequest request) {
		return ServerResponse.ok()
			.body(beerService.getById(request.pathVariable("beerId")), BeerDTO.class);
	}

	public Mono<ServerResponse> createNewBeer(ServerRequest request) {
		return beerService.saveBeer(request.bodyToMono(BeerDTO.class))
			.flatMap(beerDTO -> ServerResponse
				.created(UriComponentsBuilder.fromPath(BeerRouterConfig.PATH_ID).build(beerDTO.getId()))
				.build());
	}

	public Mono<ServerResponse> updateBeerById(ServerRequest request) {
		return request.bodyToMono(BeerDTO.class)
			.map(beerDTO -> beerService.updateBeer(request.pathVariable("beerId"), beerDTO))
			.flatMap(beerDTO -> ServerResponse.noContent().build());
	}

	public Mono<ServerResponse> patchBeerById(ServerRequest request) {
		return request.bodyToMono(BeerDTO.class)
			.map(beerDTO -> beerService.patchBeer(request.pathVariable("beerId"), beerDTO))
			.flatMap(beerDTO -> ServerResponse.noContent().build());
	}

	public Mono<ServerResponse> deleteBeerById(ServerRequest request) {
		return beerService.deleteBeer(request.pathVariable("beerId"))
			.then(ServerResponse.noContent().build());
	}
}
