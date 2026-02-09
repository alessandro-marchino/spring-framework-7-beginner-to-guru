package guru.springframework.spring7reactivemongo.mapper;

import org.mapstruct.Mapper;

import guru.springframework.spring7reactivemongo.domain.Customer;
import guru.springframework.spring7reactivemongo.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

	Customer customerDtoToCustomer(CustomerDTO customerDTO);
	CustomerDTO customerToCustomerDTO(Customer customer);
}
