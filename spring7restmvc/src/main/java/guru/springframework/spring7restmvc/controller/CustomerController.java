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

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping
	public List<Customer> listCustomers() {
		return customerService.listCustomers();
	}

	@GetMapping("/{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") UUID id) {
		log.debug("Get Customer Id in controller is called with id {}", id);
		return customerService.getCustomerById(id);
	}

	@PostMapping
	public ResponseEntity<Void> saveCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveNewCustomer(customer);
		log.debug("Saved customer with id {}", savedCustomer.getId());
		return ResponseEntity
			.created(URI.create("/api/v1/customer/" + savedCustomer.getId()))
			.build();
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {
		customerService.updateCustomerById(id, customer);
		log.debug("Updated customer with id {}", id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") UUID id) {
		customerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{customerId}")
	public ResponseEntity<Void> patchCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {
		customerService.patchCustomerById(id, customer);
		return ResponseEntity.noContent().build();
	}
}
