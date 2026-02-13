package guru.springframework.spring7reactivemongo.web.fn;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7reactivemongo.model.CustomerDTO;
import guru.springframework.spring7reactivemongo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerHandler {
	private final CustomerService service;
	private final Validator validator;

	private void validate(CustomerDTO dto) {
		Errors errors = new BeanPropertyBindingResult(dto, "customerDto");
		validator.validate(dto, errors);
		if(errors.hasErrors()) {
			throw new ServerWebInputException(errors.toString());
		}
	}

	public Mono<ServerResponse> listCustomers(ServerRequest request) {
		return ServerResponse.ok()
			.body(service.listCustomers(request.queryParam("customerName")), CustomerDTO.class);
	}

	public Mono<ServerResponse> getCustomerById(ServerRequest request) {
		return service.getById(request.pathVariable("customerId"))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(dto -> ServerResponse.ok().body(Mono.just(dto), CustomerDTO.class));
	}

	public Mono<ServerResponse> createNewCustomer(ServerRequest request) {
		return request.bodyToMono(CustomerDTO.class)
			.doOnNext(this::validate)
			.flatMap(dto -> service.saveCustomer(dto))
			.flatMap(dto -> ServerResponse
				.created(UriComponentsBuilder.fromPath(CustomerRouterConfig.PATH_ID).build(dto.getId()))
				.build());
	}

	public Mono<ServerResponse> updateCustomerById(ServerRequest request) {
		return request.bodyToMono(CustomerDTO.class)
			.doOnNext(this::validate)
			.flatMap(dto -> service.updateCustomer(request.pathVariable("customerId"), dto))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(dto -> ServerResponse.noContent().build());
	}

	public Mono<ServerResponse> patchCustomerById(ServerRequest request) {
		return request.bodyToMono(CustomerDTO.class)
			.doOnNext(this::validate)
			.flatMap(dto -> service.patchCustomer(request.pathVariable("customerId"), dto))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(beerDTO -> ServerResponse.noContent().build());
	}

	public Mono<ServerResponse> deleteCustomerById(ServerRequest request) {
		return service.getById(request.pathVariable("customerId"))
			.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			.flatMap(dto -> service.deleteCustomer(dto.getId()))
			.then(ServerResponse.noContent().build());
	}
}
