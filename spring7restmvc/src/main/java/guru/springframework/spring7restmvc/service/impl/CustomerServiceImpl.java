package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	private final Map<UUID, CustomerDTO> customerMap;

	public CustomerServiceImpl() {
		customerMap = new HashMap<>();
		CustomerDTO customer1 = CustomerDTO.builder()
			.id(UUID.randomUUID())
			.version(1)
			.customerName("Customer 1")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		CustomerDTO customer2 = CustomerDTO.builder()
			.id(UUID.randomUUID())
			.version(1)
			.customerName("Customer 2")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		CustomerDTO customer3 = CustomerDTO.builder()
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
	public List<CustomerDTO> listCustomers() {
			return List.copyOf(customerMap.values());
	}
	@Override
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {
		log.debug("Get Customer Id in service is called with id {}", customerId);
		return Optional.ofNullable(customerMap.get(customerId));
	}
	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customer) {
		CustomerDTO savedCustomer = CustomerDTO.builder()
			.id(UUID.randomUUID())
			.version(1)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.customerName(customer.getCustomerName())
			.build();
		customerMap.put(savedCustomer.getId(), savedCustomer);
		return savedCustomer;
	}

	@Override
	public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
		CustomerDTO existing = customerMap.get(customerId);
		if(existing == null) {
			return Optional.empty();
		}
		existing.setCustomerName(customer.getCustomerName());
		existing.setVersion(existing.getVersion() + 1);
		existing.setUpdatedDate(LocalDateTime.now());
		customerMap.put(customerId, existing);
		return Optional.of(existing);
	}

	@Override
	public boolean deleteById(UUID custoemrId) {
		return customerMap.remove(custoemrId) != null;
	}

	@Override
	public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
		CustomerDTO existing = customerMap.get(customerId);
		if(existing == null) {
			return Optional.empty();
		}
		if(customer.getCustomerName() != null) {
			existing.setCustomerName(customer.getCustomerName());
		}
		existing.setVersion(existing.getVersion() + 1);
		existing.setUpdatedDate(LocalDateTime.now());
		customerMap.put(customerId, existing);
		return Optional.of(existing);
	}
}
