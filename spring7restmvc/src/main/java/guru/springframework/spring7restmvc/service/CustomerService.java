package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Customer;

public interface CustomerService {

	Customer getCustomerById(UUID id);
	List<Customer> listCustomers();
	Customer saveNewCustomer(Customer customer);
}
