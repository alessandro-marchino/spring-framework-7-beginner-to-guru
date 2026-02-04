package guru.springframework.spring7reactive.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7reactive.model.BeerDTO;
import guru.springframework.spring7reactive.model.BeerMapper;
import guru.springframework.spring7reactive.repositories.BeerRepository;
import guru.springframework.spring7reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Override
	public Flux<BeerDTO> listBeers() {
		return beerRepository.findAll()
			.map(beerMapper::toBeerDTO);
	}
}
