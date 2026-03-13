package guru.springframework.kbe.kberestbrewery.service;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import guru.springframework.kbe.kberestbrewery.domain.Beer;
import guru.springframework.kbe.kberestbrewery.repositories.BeerRepository;
import guru.springframework.kbe.kberestbrewery.web.controller.NotFoundException;
import guru.springframework.kbe.kberestbrewery.web.mapper.BeerMapper;
import guru.springframework.kbe.kberestbrewery.web.model.BeerDto;
import guru.springframework.kbe.kberestbrewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	@Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
	@Override
	public Page<BeerDto> listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

		Beer beerProbe = Beer.builder()
			.beerName(beerName)
			.beerStyle(beerStyle)
			.build();
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
			.withMatcher("beerName", m -> m.ignoreCase().contains());
		Example<Beer> example = Example.of(beerProbe, matcher);
		Page<Beer> beerPage = beerRepository.findAll(example, pageRequest);
		Page<BeerDto> beerPagedList;
		if (showInventoryOnHand) {
			beerPagedList = beerPage.map(beerMapper::beerToBeerDtoWithInventory);
		} else {
			beerPagedList = beerPage.map(beerMapper::beerToBeerDto);
		}

		return beerPagedList;
	}

	@Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
	@Override
	public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
		if (showInventoryOnHand) {
			return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
		} else {
			return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
		}
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(BeerStyleEnum.valueOf(beerDto.getBeerStyle()));
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());

		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Cacheable(cacheNames = "beerUpcCache")
	@Override
	public BeerDto getByUpc(String upc) {
		return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
	}

	@Override
	public void deleteBeerById(UUID beerId) {
		beerRepository.deleteById(beerId);
	}
}
