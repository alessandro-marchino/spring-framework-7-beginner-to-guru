package guru.springframework.spring7reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import guru.springframework.spring7reactive.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}
