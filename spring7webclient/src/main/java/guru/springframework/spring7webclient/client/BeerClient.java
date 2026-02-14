package guru.springframework.spring7webclient.client;

import reactor.core.publisher.Flux;

public interface BeerClient {

	Flux<String> listBeer();
}
