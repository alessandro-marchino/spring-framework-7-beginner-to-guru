package guru.springframework.spring7reactive.mappers;

import org.mapstruct.Mapper;

import guru.springframework.spring7reactive.domain.Customer;
import guru.springframework.spring7reactive.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

	CustomerDTO toCustomerDTO(Customer customer);
	Customer toCustomer(CustomerDTO customerDTO);
}
