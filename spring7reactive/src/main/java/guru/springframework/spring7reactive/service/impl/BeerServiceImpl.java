package guru.springframework.spring7reactive.service.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7reactive.model.BeerDTO;
import guru.springframework.spring7reactive.model.BeerMapper;
import guru.springframework.spring7reactive.repositories.BeerRepository;
import guru.springframework.spring7reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@Override
	public Mono<BeerDTO> getBeerById(Integer id) {
		return beerRepository.findById(id)
			.map(beerMapper::toBeerDTO);
	}

	@Override
	public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {
		beerDTO.setId(null);
		beerDTO.setCreatedDate(null);
		beerDTO.setLastModifiedDate(null);

		return beerRepository
			.save(beerMapper.toBeer(beerDTO))
			.map(beerMapper::toBeerDTO);
	}

	@Override
	public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {
		return beerRepository.findById(beerId)
			.map(foundBeer -> {
				foundBeer.setBeerName(beerDTO.getBeerName());
				foundBeer.setUpc(beerDTO.getUpc());
				foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
				foundBeer.setPrice(beerDTO.getPrice());
				return foundBeer;
			})
			.flatMap(beerRepository::save)
			.map(beerMapper::toBeerDTO);
	}
}
