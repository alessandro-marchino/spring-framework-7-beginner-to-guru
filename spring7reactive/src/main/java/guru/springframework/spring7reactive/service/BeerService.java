package guru.springframework.spring7reactive.service;

import guru.springframework.spring7reactive.model.BeerDTO;
import reactor.core.publisher.Flux;

public interface BeerService {

	Flux<BeerDTO> listBeers();
}
