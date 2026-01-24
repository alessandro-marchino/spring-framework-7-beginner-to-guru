package guru.springframework.spring7restmvc.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;

public interface BeerService {

	int DEFAULT_PAGE_SIZE = 25;
	int MAX_PAGE_SIZE = 1000;
	int DEFAULT_PAGE_NUMBER = 0;


	Optional<BeerDTO> getBeerById(UUID beerId);
	Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventory, Integer pageNumber, Integer pageSize);
	BeerDTO saveNewBeer(BeerDTO beer);
	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);
	boolean deleteById(UUID beerId);
	Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

	default PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
		int queryPageNumber = pageNumber == null || pageNumber <= 0 ? DEFAULT_PAGE_NUMBER : (pageNumber - 1);
		int queryPageSize = pageSize == null || pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;

		return PageRequest.of(queryPageNumber, queryPageSize);
	}
}
