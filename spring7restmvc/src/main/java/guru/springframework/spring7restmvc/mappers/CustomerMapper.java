package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

	@Mapping(target = "email", ignore = true)
	Customer customerDtoToCustomer(CustomerDTO dto);
	CustomerDTO customerToCustomerDto(Customer customer);
}
