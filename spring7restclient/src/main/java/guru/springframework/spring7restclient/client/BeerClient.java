package guru.springframework.spring7restclient.client;

import java.util.UUID;

import org.springframework.data.domain.Page;

import guru.springframework.spring7restclient.model.BeerDTO;
import guru.springframework.spring7restclient.model.BeerStyle;

public interface BeerClient {

	Page<BeerDTO> listBeers();
	Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

	BeerDTO getBeerById(UUID beerId);

	BeerDTO createBeer(BeerDTO beerDTO);

	BeerDTO updateBeer(BeerDTO beerDTO);

	void deleteBeer(UUID beerId);
}
