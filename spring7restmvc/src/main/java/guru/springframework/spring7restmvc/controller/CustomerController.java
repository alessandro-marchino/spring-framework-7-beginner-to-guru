package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
}
