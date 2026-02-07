package guru.springframework.spring7reactive.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7reactive.mappers.CustomerMapper;
import guru.springframework.spring7reactive.model.CustomerDTO;
import guru.springframework.spring7reactive.repositories.CustomerRepository;
import guru.springframework.spring7reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public Flux<CustomerDTO> listCustomers() {
		return customerRepository.findAll()
			.map(customerMapper::toCustomerDTO);
	}

	@Override
	public Mono<CustomerDTO> getCustomerById(Integer id) {
		return customerRepository.findById(id)
			.map(customerMapper::toCustomerDTO);
	}

	@Override
	public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
		customerDTO.setId(null);
		customerDTO.setCreatedDate(null);
		customerDTO.setLastModifiedDate(null);

		return customerRepository
			.save(customerMapper.toCustomer(customerDTO))
			.map(customerMapper::toCustomerDTO);
	}

	@Override
	public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
		return customerRepository.findById(customerId)
			.map(foundCustomer -> {
				foundCustomer.setCustomerName(customerDTO.getCustomerName());
				return foundCustomer;
			})
			.flatMap(customerRepository::save)
			.map(customerMapper::toCustomerDTO);
	}
	@Override
	public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
		return customerRepository.findById(customerId)
			.map(foundCustomer -> {
				if(customerDTO.getCustomerName() != null) {
					foundCustomer.setCustomerName(customerDTO.getCustomerName());
				}
				return foundCustomer;
			})
			.flatMap(customerRepository::save)
			.map(customerMapper::toCustomerDTO);
	}

	@Override
	public Mono<Void> deleteCustomer(Integer customerId) {
		return customerRepository.deleteById(customerId);
	}
}
