package guru.springframework.kbe.kberestbrewery.web.controller;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.kbe.kberestbrewery.service.CustomerService;
import guru.springframework.kbe.kberestbrewery.web.model.CustomerDto;
import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {

	private CustomerService customerService;

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID customerId) {

		return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> handlePost(@RequestBody @Validated CustomerDto customerDto) {
		CustomerDto savedDto = customerService.saveNewCustomer(customerDto);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/v1/customer/" + savedDto.getId().toString());

		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}

	@PutMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleUpdate(@PathVariable UUID customerId, @Validated @RequestBody CustomerDto customerDto) {
		customerService.updateCustomer(customerId, customerDto);
	}

	@DeleteMapping("/{customerId}")
	public void deleteById(@PathVariable UUID customerId) {
		customerService.deleteById(customerId);
	}

}
