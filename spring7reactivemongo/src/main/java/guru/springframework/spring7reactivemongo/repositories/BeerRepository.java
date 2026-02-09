package guru.springframework.spring7reactivemongo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.spring7reactivemongo.domain.Beer;

public interface BeerRepository extends ReactiveMongoRepository<Beer, String> {

}
