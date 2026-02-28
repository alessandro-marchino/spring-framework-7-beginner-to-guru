package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import guru.springframework.spring7restmvc.bootstrap.BootstrapData;
import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.service.impl.BeerCsvServiceImpl;
import guru.springframework.spring7restmvc.testutil.TestUtils;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
@Import({ BootstrapData.class, BeerCsvServiceImpl.class, TestUtils.CacheConfig.class })
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
		Page<Beer> list = repository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
		assertThat(list).hasSize(336);
		assertThat(list.getTotalPages()).isEqualTo(1);
	}
	@Test
	void testGetBeerListByNamePaged() {
		Page<Beer> list = repository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", PageRequest.of(2, 50));
		assertThat(list).hasSize(50);
		assertThat(list.getTotalElements()).isEqualTo(336);
		assertThat(list.getTotalPages()).isEqualTo(7);
	}

	@Test
	void testGetBeerListByStyle() {
		Page<Beer> list = repository.findAllByBeerStyle(BeerStyle.IPA, null);
		assertThat(list).hasSize(548);
		assertThat(list.getTotalPages()).isEqualTo(1);
	}
	@Test
	void testGetBeerListByStylePaged() {
		Page<Beer> list = repository.findAllByBeerStyle(BeerStyle.IPA, PageRequest.of(2, 50));
		assertThat(list).hasSize(50);
		assertThat(list.getTotalElements()).isEqualTo(548);
		assertThat(list.getTotalPages()).isEqualTo(11);
	}

	@Test
	void testGetBeerListByNameAndStyle() {
		Page<Beer> list = repository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.IPA, null);
		assertThat(list).hasSize(310);
		assertThat(list.getTotalPages()).isEqualTo(1);
	}
	@Test
	void testGetBeerListByNameAndStylePaged() {
		Page<Beer> list = repository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.IPA, PageRequest.of(2, 50));
		assertThat(list).hasSize(50);
		assertThat(list.getTotalElements()).isEqualTo(310);
		assertThat(list.getTotalPages()).isEqualTo(7);
	}
	@Test
	void testGetBeerListByNameAndStylePagedPartialResult() {
		Page<Beer> list = repository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.IPA, PageRequest.of(6, 50));
		assertThat(list).hasSize(10);
		assertThat(list.getTotalElements()).isEqualTo(310);
		assertThat(list.getTotalPages()).isEqualTo(7);
	}
}
