package guru.springframework.spring7reactive.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7reactive.model.CustomerDTO;
import guru.springframework.spring7reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CustomerController.PATH)
@RequiredArgsConstructor
public class CustomerController {

	public static final String PATH = "/api/v2/customer";
	public static final String PATH_ID = "/{customerId}";

	private final CustomerService customerService;

	@GetMapping
	public Flux<CustomerDTO> listCustomers() {
		return customerService.listCustomers();
	}

	@GetMapping(PATH_ID)
	public Mono<CustomerDTO> getCustomerById(@PathVariable Integer customerId) {
		return customerService.getCustomerById(customerId);
	}

	@PostMapping
	public Mono<ResponseEntity<Void>> createNewCustomer(@RequestBody @Validated CustomerDTO customerDTO) {
		return customerService.saveNewCustomer(customerDTO)
			.map(savedDto -> ResponseEntity.created(UriComponentsBuilder.fromUriString("http://localhost:8080" + PATH + PATH_ID).build(savedDto.getId())).build());
	}

	@PutMapping(PATH_ID)
	public Mono<ResponseEntity<Void>> updateExistingCustomer(@PathVariable Integer customerId, @RequestBody @Validated CustomerDTO customerDTO) {
		return customerService.updateCustomer(customerId, customerDTO)
			.map(_ -> ResponseEntity.noContent().build());
	}

	@PatchMapping(PATH_ID)
	public Mono<ResponseEntity<Void>> patchExistingCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customerDTO) {
		return customerService.patchCustomer(customerId, customerDTO)
			.map(_ -> ResponseEntity.noContent().build());
	}

	@DeleteMapping(PATH_ID)
	public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer customerId) {
		return customerService.deleteCustomer(customerId)
			.thenReturn(ResponseEntity.noContent().build());
	}
}
