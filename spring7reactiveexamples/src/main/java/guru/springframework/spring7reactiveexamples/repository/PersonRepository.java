package guru.springframework.spring7reactiveexamples.repository;

import guru.springframework.spring7reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

	Mono<Person> getById(Integer id);
	Flux<Person> findAll();
}
