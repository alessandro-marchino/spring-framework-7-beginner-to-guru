package guru.springframework.spring7reactivemongo.web.fn;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerHandler {
	private final BeerService beerService;

	public Mono<ServerResponse> listBeers(ServerRequest request) {
		return ServerResponse.ok()
			.body(beerService.listBeers(), BeerDTO.class);
	}

	public Mono<ServerResponse> getBeerById(ServerRequest request) {
		return beerService.getById(request.pathVariable("beerId"))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(beerDto -> ServerResponse.ok().body(Mono.just(beerDto), BeerDTO.class));
	}

	public Mono<ServerResponse> createNewBeer(ServerRequest request) {
		return beerService.saveBeer(request.bodyToMono(BeerDTO.class))
			.flatMap(beerDTO -> ServerResponse
				.created(UriComponentsBuilder.fromPath(BeerRouterConfig.PATH_ID).build(beerDTO.getId()))
				.build());
	}

	public Mono<ServerResponse> updateBeerById(ServerRequest request) {
		return request.bodyToMono(BeerDTO.class)
			.flatMap(beerDTO -> {
				log.info("BeerDTO: {}", beerDTO);
				return beerService.updateBeer(request.pathVariable("beerId"), beerDTO);
			})
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(beerDTO -> ServerResponse.noContent().build());
	}

	public Mono<ServerResponse> patchBeerById(ServerRequest request) {
		return request.bodyToMono(BeerDTO.class)
			.flatMap(beerDTO -> beerService.patchBeer(request.pathVariable("beerId"), beerDTO))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(beerDTO -> ServerResponse.noContent().build());
	}

	public Mono<ServerResponse> deleteBeerById(ServerRequest request) {
		return beerService.getById(request.pathVariable("beerId"))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(beerDto -> beerService.deleteBeer(beerDto.getId()))
			.then(ServerResponse.noContent().build());
	}
}
