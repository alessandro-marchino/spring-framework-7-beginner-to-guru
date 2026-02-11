package guru.springframework.spring7reactivemongo.services;

import guru.springframework.spring7reactivemongo.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

	Flux<BeerDTO> listBeers();
	Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDto);
	Mono<BeerDTO> saveBeer(BeerDTO beerDto);
	Mono<BeerDTO> getById(String beerId);
	Mono<BeerDTO> findFirstByBeerName(String beerName);
	Flux<BeerDTO> findByBeerStyle(String beerStyle);
	Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDto);
	Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDto);
	Mono<Void> deleteBeer(String beerId);

}
