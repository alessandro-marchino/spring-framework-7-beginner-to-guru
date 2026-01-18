package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;

public interface BeerService {

	Beer getBeerById(UUID id);
	List<Beer> listBeers();
	Beer saveNewBeer(Beer beer);
	void updateBeerById(UUID beerId, Beer beer);
	void deleteById(UUID id);
}
