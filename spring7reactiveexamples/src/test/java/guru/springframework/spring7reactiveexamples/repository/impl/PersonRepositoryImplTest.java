package guru.springframework.spring7reactiveexamples.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import guru.springframework.spring7reactiveexamples.domain.Person;
import guru.springframework.spring7reactiveexamples.repository.PersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PersonRepositoryImplTest {

	PersonRepository personRepository = new PersonRepositoryImpl();

	@Test
	void testGetByIdBlock() {
		// Not preferred
		Mono<Person> personMono = personRepository.getById(1);
		Person person = personMono.block();
		assertThat(person.getFirstName()).isEqualTo("Michael");
	}
	@Test
	void testGetByIdSubscriber() {
		Mono<Person> personMono = personRepository.getById(1);
		personMono.subscribe(person -> assertThat(person.getFirstName()).isEqualTo("Michael"));
	}
	@Test
	void testMapOperation() {
		Mono<Person> personMono = personRepository.getById(1);
		personMono
			.map(Person::getFirstName)
			.subscribe(firstName -> assertThat(firstName).isEqualTo("Michael"));
	}

	@Test
	void testFluxBlock() {
		// Not preferred
		Flux<Person> personFlux = personRepository.findAll();
		Person person = personFlux.blockFirst();
		assertThat(person.getFirstName()).isEqualTo("Michael");
	}
	@Test
	void testFluxSubscriber() {
		Flux<Person> personFlux = personRepository.findAll();
		personFlux.subscribe(person -> assertThat(person).isNotNull());
	}
	@Test
	void testFluxMap() {
		Flux<Person> personFlux = personRepository.findAll();
		personFlux
			.map(Person::getFirstName)
			.subscribe(firstName -> assertThat(firstName).isNotBlank());
	}
	@Test
	void testFluxToList() {
		Flux<Person> personFlux = personRepository.findAll();
		Mono<List<Person>> listMono = personFlux.collectList();
		listMono
			.subscribe(list -> assertThat(list).hasSize(4));
	}
	@Test
	void testFilterOnName() {
		personRepository.findAll()
			.filter(person -> "Fiona".equals(person.getFirstName()))
			.subscribe(person -> assertThat(person.getFirstName()).isEqualTo("Fiona"));
	}
	@Test
	void testGetById() {
		Mono<Person> fionaMono = personRepository.findAll()
			.filter(person -> "Fiona".equals(person.getFirstName()))
			.next();
		fionaMono.subscribe(person -> assertThat(person.getFirstName()).isEqualTo("Fiona"));
	}
}
