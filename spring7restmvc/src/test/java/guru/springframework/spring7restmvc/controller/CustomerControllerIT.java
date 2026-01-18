package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;

@SpringBootTest
public class CustomerControllerIT {
	@Autowired CustomerController controller;
	@Autowired CustomerRepository repository;
	@Autowired CustomerMapper mapper;

	@Test
	@Transactional
	@Rollback
    void testDeleteCustomer() {
		Customer entity = repository.findAll().getFirst();
		ResponseEntity<Void> responseEntity = controller.deleteCustomer(entity.getId());
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));

		Optional<Customer> foundBeer = repository.findById(entity.getId());
		assertThat(foundBeer).isEmpty();
    }

	@Test
    void testDeleteBeerNotFound() {
		assertThrows(NotFoundException.class, () -> controller.deleteCustomer(UUID.randomUUID()));
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
	void testPatchCustomerNotFound() {
		assertThrows(NotFoundException.class, () -> controller.patchCustomer(UUID.randomUUID(), CustomerDTO.builder().build()));
	}

    @Test
	@Transactional
	@Rollback
    void testPatchBeer() {
		Customer entity = repository.findAll().getFirst();
		CustomerDTO dto = mapper.customerToCustomerDto(entity);
		final String name = "UPDATED";
		dto.setCustomerName(name);

		ResponseEntity<Void> responseEntity = controller.patchCustomer(entity.getId(), dto);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
		repository.flush();

		Customer updatedEntity = repository.findById(entity.getId()).get();
		assertThat(updatedEntity).isNotNull();
		assertThat(updatedEntity.getCustomerName()).isEqualTo(name);
		assertThat(updatedEntity.getVersion()).isEqualTo(1);
    }

    @Test
	@Transactional
	@Rollback
    void testSaveCustomer() {
		CustomerDTO dto = CustomerDTO.builder()
			.customerName("New Customer")
			.build();
		ResponseEntity<Void> responseEntity = controller.saveCustomer(dto);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
		assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
		String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
		UUID savedUUID = UUID.fromString(locationUUID[4]);

		Customer entity = repository.findById(savedUUID).get();
		assertThat(entity).isNotNull();
		assertThat(entity.getCustomerName()).isEqualTo("New Customer");
		assertThat(entity.getVersion()).isEqualTo(0);
    }

    @Test
    void testUpdateCustomerNotFound() {
		assertThrows(NotFoundException.class, () -> controller.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Test
	@Transactional
	@Rollback
    void testUpdateCustomer() {
		Customer entity = repository.findAll().getFirst();
		CustomerDTO dto = mapper.customerToCustomerDto(entity);
		final String name = "UPDATED";
		dto.setCustomerName(name);

		ResponseEntity<Void> responseEntity = controller.updateCustomer(entity.getId(), dto);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
		repository.flush();

		Customer updatedCustomer = repository.findById(entity.getId()).get();
		assertThat(updatedCustomer).isNotNull();
		assertThat(updatedCustomer.getCustomerName()).isEqualTo(name);
		assertThat(updatedCustomer.getVersion()).isEqualTo(1);
    }
}
