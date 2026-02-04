package guru.springframework.spring7reactive.service;

import guru.springframework.spring7reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

	Flux<BeerDTO> listBeers();
	Mono<BeerDTO> getBeerById(Integer id);
	Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);
}
