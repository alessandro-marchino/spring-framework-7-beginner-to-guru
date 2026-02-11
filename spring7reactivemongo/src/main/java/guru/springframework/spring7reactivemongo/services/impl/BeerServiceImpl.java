package guru.springframework.spring7reactivemongo.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7reactivemongo.mapper.BeerMapper;
import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.repositories.BeerRepository;
import guru.springframework.spring7reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Override
	public Flux<BeerDTO> listBeers() {
		return beerRepository.findAll()
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDto) {
		return beerDto.map(beerMapper::beerDtoToBeer)
			.flatMap(beerRepository::save)
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> saveBeer(BeerDTO beerDto) {
		return beerRepository.save(beerMapper.beerDtoToBeer(beerDto))
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> getById(String beerId) {
		return beerRepository.findById(beerId)
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> findFirstByBeerName(String beerName) {
		return beerRepository.findFirstByBeerName(beerName)
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDto) {
		return beerRepository.findById(beerId)
			.map(foundBeer -> {
				foundBeer.setBeerName(beerDto.getBeerName());
				foundBeer.setBeerStyle(beerDto.getBeerStyle());
				foundBeer.setPrice(beerDto.getPrice());
				foundBeer.setUpc(beerDto.getUpc());
				foundBeer.setQuantityOnHand(beerDto.getQuantityOnHand());
				return foundBeer;
			})
			.flatMap(beerRepository::save)
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDto) {
		return beerRepository.findById(beerId)
			.map(foundBeer -> {
				if(beerDto.getBeerName() != null) {
					foundBeer.setBeerName(beerDto.getBeerName());
				}
				if(beerDto.getBeerStyle() != null) {
					foundBeer.setBeerStyle(beerDto.getBeerStyle());
				}
				if(beerDto.getPrice() != null) {
					foundBeer.setPrice(beerDto.getPrice());
				}
				if(beerDto.getUpc() != null) {
					foundBeer.setUpc(beerDto.getUpc());
				}
				if(beerDto.getQuantityOnHand() != null) {
					foundBeer.setQuantityOnHand(beerDto.getQuantityOnHand());
				}
				return foundBeer;
			})
			.flatMap(beerRepository::save)
			.map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<Void> deleteBeer(String beerId) {
		return beerRepository.deleteById(beerId);
	}
}
