package guru.springframework.spring7restmvc.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

	public static final String PATH = "/api/v1/customer";
	public static final String PATH_ID = PATH + "/{customerId}";
	private final CustomerService customerService;

	@GetMapping(PATH)
	public List<CustomerDTO> listCustomers() {
		return customerService.listCustomers();
	}

	@GetMapping(PATH_ID)
	public CustomerDTO getCustomerById(@PathVariable("customerId") UUID id) {
		log.debug("Get Customer Id in controller is called with id {}", id);
		return customerService.getCustomerById(id)
			.orElseThrow(NotFoundException::new);
	}

	@PostMapping(PATH)
	public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customer) {
		customer.setId(null);
		customer.setVersion(null);
		CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);
		log.debug("Saved customer with id {}", savedCustomer.getId());
		return ResponseEntity
			.created(URI.create("/api/v1/customer/" + savedCustomer.getId()))
			.build();
	}

	@PutMapping(PATH_ID)
	public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer) {
		customer.setId(null);
		customer.setVersion(null);
		customerService.updateCustomerById(id, customer).orElseThrow(NotFoundException::new);
		log.debug("Updated customer with id {}", id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(PATH_ID)
	public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") UUID id) {
		if(!customerService.deleteById(id)) {
			throw new NotFoundException();
		}
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(PATH_ID)
	public ResponseEntity<Void> patchCustomer(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer) {
		customerService.patchCustomerById(id, customer).orElseThrow(NotFoundException::new);
		return ResponseEntity.noContent().build();
	}
}
