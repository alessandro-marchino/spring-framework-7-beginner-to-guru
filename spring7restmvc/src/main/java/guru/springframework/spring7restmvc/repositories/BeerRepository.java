package guru.springframework.spring7restmvc.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

	List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);
	List<Beer> findAllByBeerStyle(BeerStyle beerStyle);
	List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle);
}
