package guru.springframework.spring7reactivemongo.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import guru.springframework.spring7reactivemongo.domain.Customer;
import guru.springframework.spring7reactivemongo.mapper.CustomerMapper;
import guru.springframework.spring7reactivemongo.model.CustomerDTO;
import guru.springframework.spring7reactivemongo.repositories.CustomerRepository;
import guru.springframework.spring7reactivemongo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository repository;
	private final CustomerMapper mapper;

	@Override
	public Flux<CustomerDTO> listCustomers(Optional<String> customerName) {
		Customer probe = Customer.builder()
			.customerName(customerName.orElse(null))
			.build();
		return repository.findAll(Example.of(probe))
			.map(mapper::customerToCustomerDTO);
	}
	@Override
	public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDto) {
		return customerDto.map(mapper::customerDtoToCustomer)
			.flatMap(repository::save)
			.map(mapper::customerToCustomerDTO);
	}
	@Override
	public Mono<CustomerDTO> saveCustomer(CustomerDTO customerDto) {
		return repository.save(mapper.customerDtoToCustomer(customerDto))
			.map(mapper::customerToCustomerDTO);
	}
	@Override
	public Mono<CustomerDTO> getById(String customerId) {
		return repository.findById(customerId)
			.map(mapper::customerToCustomerDTO);
	}
	@Override
	public Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDto) {
		return repository.findById(customerId)
			.map(entity -> {
				entity.setCustomerName(customerDto.getCustomerName());
				return entity;
			})
			.flatMap(repository::save)
			.map(mapper::customerToCustomerDTO);
	}
	@Override
	public Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO customerDto) {
		return repository.findById(customerId)
			.map(entity -> {
				if(customerDto.getCustomerName() != null) {
					entity.setCustomerName(customerDto.getCustomerName());
				}
				return entity;
			})
			.flatMap(repository::save)
			.map(mapper::customerToCustomerDTO);
	}
	@Override
	public Mono<Void> deleteCustomer(String customerId) {
		return repository.deleteById(customerId);
	}


}
