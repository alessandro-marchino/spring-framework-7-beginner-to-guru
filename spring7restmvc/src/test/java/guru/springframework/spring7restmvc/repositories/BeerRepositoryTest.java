package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import guru.springframework.spring7restmvc.bootstrap.BootstrapData;
import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.service.impl.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
@Import({ BootstrapData.class, BeerCsvServiceImpl.class })
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

	@Test
	void testSaveBeerTooLong() {
		assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(Beer.builder()
			.beerName("My beer 12345678901234567890123456789012345678901234567890")
			.beerStyle(BeerStyle.GOSE)
			.upc("123456789")
			.price(new BigDecimal("12.99"))
			.build()));
	}

	@Test
	void testGetBeerListByName() {
		List<Beer> list = repository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
		assertThat(list).hasSize(336);
	}
}
