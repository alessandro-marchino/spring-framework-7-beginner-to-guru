package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.BeerDTO;

public interface BeerService {

	Optional<BeerDTO> getBeerById(UUID beerId);
	List<BeerDTO> listBeers();
	BeerDTO saveNewBeer(BeerDTO beer);
	void updateBeerById(UUID beerId, BeerDTO beer);
	void deleteById(UUID beerId);
	void patchBeerById(UUID beerId, BeerDTO beer);
}
