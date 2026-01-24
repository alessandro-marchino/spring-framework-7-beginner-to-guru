package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Beer_;
import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class BeerServiceJPA implements BeerService {
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Override
	public Optional<BeerDTO> getBeerById(UUID beerId) {
		return beerRepository.findById(beerId)
			.map(beerMapper::beerToBeerDto);
	}

	@Override
	public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventory) {
		Beer beerProbe = Beer.builder()
			.beerName(beerName)
			.beerStyle(beerStyle)
			.build();
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
			.withMatcher(Beer_.BEER_NAME, m -> m.ignoreCase().contains());
		Example<Beer> example = Example.of(beerProbe, matcher);
		return beerRepository.findAll(example)
			.stream()
			.map(beer -> {
				if(!showInventory) {
					beer.setQuantityOnHand(null);
				}
				return beer;
			})
			.map(beerMapper::beerToBeerDto)
			.toList();
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {
		LocalDateTime now = LocalDateTime.now();
		beer.setCreatedDate(now);
		beer.setCreatedDate(now);
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO dto) {
		AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
		beerRepository.findById(beerId).ifPresentOrElse(beer -> {
			beer.setBeerName(dto.getBeerName());
			beer.setBeerStyle(dto.getBeerStyle());
			beer.setUpc(dto.getUpc());
			beer.setPrice(dto.getPrice());
			beer.setQuantityOnHand(dto.getQuantityOnHand());
			beer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(beer))));
		}, () -> reference.set(Optional.empty()));
		return reference.get();
	}

	@Override
	public boolean deleteById(UUID beerId) {
		if(!beerRepository.existsById(beerId)) {
			return false;
		}
		beerRepository.deleteById(beerId);
		return true;
	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
		beerRepository.findById(beerId).ifPresentOrElse(beer -> {
			if(dto.getBeerName() != null) {
				beer.setBeerName(dto.getBeerName());
			}
			if(dto.getBeerStyle() != null) {
				beer.setBeerStyle(dto.getBeerStyle());
			}
			if(dto.getUpc() != null) {
				beer.setUpc(dto.getUpc());
			}
			if(dto.getPrice() != null) {
				beer.setPrice(dto.getPrice());
			}
			if(dto.getQuantityOnHand() != null) {
				beer.setQuantityOnHand(dto.getQuantityOnHand());
			}
			beer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.saveAndFlush(beer))));
		}, () -> reference.set(Optional.empty()));
		return reference.get();
	}

}
