package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Customer;

public interface CustomerService {

	Optional<Customer> getCustomerById(UUID customerId);
	List<Customer> listCustomers();
	Customer saveNewCustomer(Customer customer);
	void updateCustomerById(UUID customerId, Customer customer);
	void deleteById(UUID customerId);
	void patchCustomerById(UUID customerId, Customer customer);
}
