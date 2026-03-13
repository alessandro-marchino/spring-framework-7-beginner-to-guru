package guru.springframework.kbe.kberestbrewery.web.mapper;

import org.mapstruct.Mapper;

import guru.springframework.kbe.kberestbrewery.domain.Customer;
import guru.springframework.kbe.kberestbrewery.web.model.CustomerDto;

@Mapper
public interface CustomerMapper {
	Customer customerDtoToCustomer(CustomerDto dto);

	CustomerDto customerToCustomerDto(Customer customer);
}
