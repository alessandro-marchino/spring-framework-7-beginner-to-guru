package guru.springframework.spring7reactive.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

import guru.springframework.spring7reactive.bootstrap.BootstrapData;
import guru.springframework.spring7reactive.config.DatabaseConfig;
import guru.springframework.spring7reactive.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import({ DatabaseConfig.class, BootstrapData.class })
@Slf4j
public class CustomerRepositoryTest {

	@Autowired CustomerRepository repository;
	@Autowired ReactiveTransactionManager reactiveTransactionManager;

	@Test
	void saveNewCustomer() {
		StepVerifier.create(
			TransactionalOperator.create(reactiveTransactionManager)
			.execute(status -> {
				status.setRollbackOnly();
				return repository.save(getTestCustomer());
			}))
			.assertNext(customer -> {
				log.warn("ID: {}", customer.getId());
				assertThat(customer.getId()).isNotNull();
				assertThat(customer.getCreatedDate()).isNotNull();
				assertThat(customer.getLastModifiedDate()).isNotNull();
			})
			.verifyComplete();
	}

	Customer getTestCustomer() {
		return Customer.builder()
			.customerName("Space Dust")
			.build();
	}
}
