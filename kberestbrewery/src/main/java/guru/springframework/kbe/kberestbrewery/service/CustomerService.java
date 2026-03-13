package guru.springframework.kbe.kberestbrewery.service;

import java.util.UUID;

import guru.springframework.kbe.kberestbrewery.web.model.CustomerDto;

public interface CustomerService {
	CustomerDto getCustomerById(UUID customerId);

	CustomerDto saveNewCustomer(CustomerDto customerDto);

	void updateCustomer(UUID customerId, CustomerDto customerDto);

	void deleteById(UUID customerId);
}
