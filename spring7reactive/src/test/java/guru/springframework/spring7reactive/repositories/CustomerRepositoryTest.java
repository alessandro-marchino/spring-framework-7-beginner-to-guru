package guru.springframework.spring7reactive.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import guru.springframework.spring7reactive.config.DatabaseConfig;
import guru.springframework.spring7reactive.domain.Customer;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository repository;

	@Test
	void saveNewCustomer() {
		StepVerifier.create(repository.save(getTestCustomer()))
			.assertNext(customer -> {
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
