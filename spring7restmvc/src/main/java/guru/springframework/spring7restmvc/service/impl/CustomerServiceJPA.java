package guru.springframework.spring7restmvc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'saveNewCustomer'");
	}

	@Override
	public void updateCustomerById(UUID customerId, CustomerDTO customer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateCustomerById'");
	}

	@Override
	public void deleteById(UUID customerId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
	}

	@Override
	public void patchCustomerById(UUID customerId, CustomerDTO customer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'patchCustomerById'");
	}

}
