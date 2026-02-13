package guru.springframework.spring7reactivemongo.services;

import java.util.Optional;

import guru.springframework.spring7reactivemongo.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

	Flux<CustomerDTO> listCustomers(Optional<String> customerName);
	Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDto);
	Mono<CustomerDTO> saveCustomer(CustomerDTO customerDto);
	Mono<CustomerDTO> getById(String customerId);
	Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDto);
	Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO customerDto);
	Mono<Void> deleteCustomer(String customerId);
}
