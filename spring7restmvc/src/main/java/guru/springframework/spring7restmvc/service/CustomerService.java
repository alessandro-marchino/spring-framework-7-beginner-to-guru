package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.CustomerDTO;

public interface CustomerService {

	Optional<CustomerDTO> getCustomerById(UUID customerId);
	List<CustomerDTO> listCustomers();
	CustomerDTO saveNewCustomer(CustomerDTO customer);
	void updateCustomerById(UUID customerId, CustomerDTO customer);
	void deleteById(UUID customerId);
	void patchCustomerById(UUID customerId, CustomerDTO customer);
}
