package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;

public interface BeerService {

	Optional<BeerDTO> getBeerById(UUID beerId);
	List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventory, Integer pageNumber, Integer pageSize);
	BeerDTO saveNewBeer(BeerDTO beer);
	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);
	boolean deleteById(UUID beerId);
	Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

}
