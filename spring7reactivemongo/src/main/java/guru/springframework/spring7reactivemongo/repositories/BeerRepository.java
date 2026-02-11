package guru.springframework.spring7reactivemongo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.spring7reactivemongo.domain.Beer;
import reactor.core.publisher.Mono;

public interface BeerRepository extends ReactiveMongoRepository<Beer, String> {

	Mono<Beer> findFirstByBeerName(String beerName);
}
