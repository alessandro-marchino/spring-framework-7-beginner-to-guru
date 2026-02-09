package guru.springframework.spring7reactivemongo.services;

import guru.springframework.spring7reactivemongo.model.BeerDTO;
import reactor.core.publisher.Mono;

public interface BeerService {

	Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDto);
	Mono<BeerDTO> getById(String beerId);
}
