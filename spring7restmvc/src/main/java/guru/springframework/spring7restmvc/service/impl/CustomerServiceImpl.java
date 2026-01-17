package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	private final Map<UUID, Customer> customerMap;

	public CustomerServiceImpl() {
		customerMap = new HashMap<>();
		Customer customer1 = Customer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.customerName("Customer 1")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		Customer customer2 = Customer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.customerName("Customer 2")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		Customer customer3 = Customer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.customerName("Customer 3")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();

		customerMap.put(customer1.getId(), customer1);
		customerMap.put(customer2.getId(), customer2);
		customerMap.put(customer3.getId(), customer3);
	}

	@Override
	public List<Customer> listCustomers() {
			return List.copyOf(customerMap.values());
	}
	@Override
	public Customer getCustomerById(UUID id) {
		log.debug("Get Customer Id in service is called with id {}", id);
		return customerMap.get(id);
	}
	@Override
	public Customer saveNewCustomer(Customer customer) {
		Customer savedCustomer = Customer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.customerName(customer.getCustomerName())
			.build();
		customerMap.put(savedCustomer.getId(), savedCustomer);
		return savedCustomer;
	}
}
