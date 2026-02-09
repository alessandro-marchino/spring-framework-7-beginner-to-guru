package guru.springframework.spring7reactivemongo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.spring7reactivemongo.domain.Customer;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

}
