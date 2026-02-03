package guru.springframework.spring7reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import guru.springframework.spring7reactive.domain.Beer;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {

}
