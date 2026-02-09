package guru.springframework.spring7reactivemongo.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.services.BeerService;
import reactor.core.publisher.Mono;

@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public Mono<BeerDTO> saveBeer(BeerDTO beerDto) {
		return null;
	}

	@Override
	public Mono<BeerDTO> getById(String beerId) {
		return null;
	}
}
