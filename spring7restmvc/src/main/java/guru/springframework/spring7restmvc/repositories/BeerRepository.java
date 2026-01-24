package guru.springframework.spring7restmvc.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

	Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, PageRequest page);
	Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, PageRequest page);
	Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, PageRequest page);
}
