package guru.springframework.kbe.kberestbrewery.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import guru.springframework.kbe.kberestbrewery.web.model.BeerDto;
import guru.springframework.kbe.kberestbrewery.web.model.BeerStyleEnum;

public interface BeerService {
	Page<BeerDto> listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

	BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

	BeerDto saveNewBeer(BeerDto beerDto);

	BeerDto updateBeer(UUID beerId, BeerDto beerDto);

	BeerDto getByUpc(String upc);

	void deleteBeerById(UUID beerId);
}
