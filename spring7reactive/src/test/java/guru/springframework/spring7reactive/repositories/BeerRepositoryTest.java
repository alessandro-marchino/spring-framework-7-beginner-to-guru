package guru.springframework.spring7reactive.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

import guru.springframework.spring7reactive.bootstrap.BootstrapData;
import guru.springframework.spring7reactive.config.DatabaseConfig;
import guru.springframework.spring7reactive.domain.Beer;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import({ DatabaseConfig.class, BootstrapData.class })
class BeerRepositoryTest {

	@Autowired BeerRepository repository;
	@Autowired ReactiveTransactionManager reactiveTransactionManager;

	@Test
	void saveNewBeer() {
		StepVerifier.create(
			TransactionalOperator.create(reactiveTransactionManager)
			.execute(status -> {
				status.setRollbackOnly();
				return repository.save(getTestBeer());
			}))
			.assertNext(beer -> {
				assertThat(beer.getId()).isNotNull();
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
