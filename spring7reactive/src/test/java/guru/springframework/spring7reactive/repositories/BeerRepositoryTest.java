package guru.springframework.spring7reactive.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;

import guru.springframework.spring7reactive.domain.Beer;
import reactor.test.StepVerifier;

@DataR2dbcTest
class BeerRepositoryTest {

	@Autowired
	BeerRepository repository;

	@Test
	void saveNewBeer() {
		StepVerifier.create(repository.save(getTestBeer()))
			.assertNext(beer -> assertThat(beer.getId()).isEqualTo(1))
			.verifyComplete();
	}

	Beer getTestBeer() {
		return Beer.builder()
			.beerName("Space Dust")
			.beerStyle("IPA")
			.price(BigDecimal.TEN)
			.quantityOnHand(12)
			.upc("123123")
			.build();
	}
}
