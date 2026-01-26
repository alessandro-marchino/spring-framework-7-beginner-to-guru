package guru.springframework.spring7resttemplate.client;

import java.util.UUID;

import org.springframework.data.domain.Page;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;

public interface BeerClient {

	Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
	Page<BeerDTO> listBeers();
	Page<BeerDTO> listBeers(Integer pageNumber, Integer pageSize);

	BeerDTO getBeerById(UUID beerId, Boolean showInventory);

	BeerDTO createBeer(BeerDTO beerDTO);

	BeerDTO updateBeer(BeerDTO beerDTO);

	void deleteBeer(UUID beerId);
}
