package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceJPA implements CustomerService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {
		return customerRepository.findById(customerId)
			.map(customerMapper::customerToCustomerDto);
	}

	@Override
	public List<CustomerDTO> listCustomers() {
		return customerRepository.findAll()
			.stream()
			.map(customerMapper::customerToCustomerDto)
			.toList();
	}

	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customer) {
		LocalDateTime now = LocalDateTime.now();
		customer.setCreatedDate(now);
		customer.setUpdatedDate(now);
		return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
	}

	@Override
	public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<CustomerDTO>> reference = new AtomicReference<>();
		customerRepository.findById(customerId).ifPresentOrElse(customer -> {
			customer.setCustomerName(dto.getCustomerName());
			customer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(customer))));
		}, () -> reference.set(Optional.empty()));
		return reference.get();
	}

	@Override
	public boolean deleteById(UUID customerId) {
		if(!customerRepository.existsById(customerId)) {
			return false;
		}
		customerRepository.deleteById(customerId);
		return true;
	}

	@Override
	public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<CustomerDTO>> reference = new AtomicReference<>();
		customerRepository.findById(customerId).ifPresentOrElse(customer -> {
			if(dto.getCustomerName() != null) {
				customer.setCustomerName(dto.getCustomerName());
			}
			customer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(customer))));
		}, () -> reference.set(Optional.empty()));
		return reference.get();
	}

}
