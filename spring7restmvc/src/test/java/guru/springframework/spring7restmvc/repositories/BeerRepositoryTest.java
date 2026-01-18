package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.spring7restmvc.entities.Beer;

@DataJpaTest
class BeerRepositoryTest {
	@Autowired BeerRepository repository;

	@Test
	void testSaveBeer() {
		Beer savedBeer = repository.save(Beer.builder()
			.beerName("My beer")
			.build());
		assertThat(savedBeer).isNotNull();
		assertThat(savedBeer.getId()).isNotNull();
	}
}
