package guru.springframework.spring7reactiveexamples.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import guru.springframework.spring7reactiveexamples.domain.Person;
import guru.springframework.spring7reactiveexamples.repository.PersonRepository;
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
}
