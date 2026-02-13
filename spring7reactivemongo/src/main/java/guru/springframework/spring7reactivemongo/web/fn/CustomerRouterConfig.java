package guru.springframework.spring7reactivemongo.web.fn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouterConfig {
	public static final String PATH = "/api/v3/customer";
	public static final String PATH_ID = PATH + "/{customerId}";

	@Bean
	RouterFunction<ServerResponse> customerRoutes(CustomerHandler handler) {
		return RouterFunctions.route()
			.GET(PATH, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::listCustomers)
			.GET(PATH_ID, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getCustomerById)
			.POST(PATH, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::createNewCustomer)
			.PUT(PATH_ID, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::updateCustomerById)
			.PATCH(PATH_ID, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::patchCustomerById)
			.DELETE(PATH_ID, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::deleteCustomerById)
			.build();
	}

}
