package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;

@SpringBootTest
public class CustomerControllerIT {
	@Autowired CustomerController controller;
	@Autowired CustomerRepository repository;

	@Test
	@Disabled
    void testDeleteCustomer() {

    }

    @Test
    void testGetCustomerById() {
		Customer customer = repository.findAll().getFirst();
		CustomerDTO dto = controller.getCustomerById(customer.getId());

		assertThat(dto).isNotNull();
    }

	@Test
    void testGetCustomerByIdNotFound() {
		assertThrows(NotFoundException.class, () -> controller.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testListCustomers() {
		List<CustomerDTO> dtos = controller.listCustomers();
		assertThat(dtos).hasSize(3);
    }

	@Test
	@Transactional
	@Rollback
    void testListCustomersEmpty() {
		repository.deleteAll();
		List<CustomerDTO> dtos = controller.listCustomers();
		assertThat(dtos).hasSize(0);
    }

    @Test
	@Disabled
    void testPatchCustomer() {

    }

    @Test
	@Disabled
    void testSaveCustomer() {

    }

    @Test
	@Disabled
    void testUpdateCustomer() {

    }
}
