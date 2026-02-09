package guru.springframework.spring7reactivemongo.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7reactivemongo.mapper.BeerMapper;
import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.repositories.BeerRepository;
import guru.springframework.spring7reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Override
	public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDto) {
		return beerDto.map(beerMapper::beerDtoToBeer)
			.flatMap(beerRepository::save)
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> getById(String beerId) {
		return null;
	}
}
