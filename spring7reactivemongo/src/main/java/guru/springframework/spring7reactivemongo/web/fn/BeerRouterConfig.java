package guru.springframework.spring7reactivemongo.web.fn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BeerRouterConfig {
	public static final String PATH = "/api/v3/beer";
	public static final String PATH_ID = PATH + "/{beerId}";

	@Bean
	RouterFunction<ServerResponse> beerRoutes(BeerHandler handler) {
		return RouterFunctions.route()
			.GET(PATH, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::listBeers)
			.GET(PATH_ID, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getBeerById)
			.POST(PATH, RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::createNewBeer)
			.build();
	}
}
