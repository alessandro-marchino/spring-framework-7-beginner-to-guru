package guru.springframework.spring7reactive.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import guru.springframework.spring7reactive.config.DatabaseConfig;
import guru.springframework.spring7reactive.domain.Beer;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class BeerRepositoryTest {

	@Autowired
	BeerRepository repository;

	@Test
	void saveNewBeer() {
		StepVerifier.create(repository.save(getTestBeer()))
			.assertNext(beer -> {
				assertThat(beer.getId()).isEqualTo(1);
				assertThat(beer.getCreatedDate()).isNotNull();
				assertThat(beer.getLastModifiedDate()).isNotNull();
			})
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
