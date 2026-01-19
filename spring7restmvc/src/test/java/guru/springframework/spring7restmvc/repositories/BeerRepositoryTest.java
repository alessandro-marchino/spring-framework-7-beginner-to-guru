package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;

@DataJpaTest
class BeerRepositoryTest {
	@Autowired BeerRepository repository;

	@Test
	void testSaveBeer() {
		Beer savedBeer = repository.saveAndFlush(Beer.builder()
			.beerName("My beer")
			.beerStyle(BeerStyle.GOSE)
			.upc("123456789")
			.price(new BigDecimal("12.99"))
			.build());
		assertThat(savedBeer).isNotNull();
		assertThat(savedBeer.getId()).isNotNull();
	}
}
