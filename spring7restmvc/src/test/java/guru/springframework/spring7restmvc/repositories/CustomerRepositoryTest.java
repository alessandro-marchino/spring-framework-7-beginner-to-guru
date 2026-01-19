package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.spring7restmvc.entities.Customer;

@DataJpaTest
class CustomerRepositoryTest {
	@Autowired CustomerRepository repository;

	@Test
	void testSaveCustomer() {
		Customer savedCustomer = repository.saveAndFlush(Customer.builder()
			.customerName("New name")
			.build());
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isNotNull();
		assertThat(savedCustomer.getCustomerName()).isEqualTo("New name");
	}
}
