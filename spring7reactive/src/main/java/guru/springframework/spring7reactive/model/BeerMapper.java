package guru.springframework.spring7reactive.model;

import org.mapstruct.Mapper;

import guru.springframework.spring7reactive.domain.Beer;

@Mapper
public interface BeerMapper {

	BeerDTO toBeerDTO(Beer beer);
	Beer toBeer(BeerDTO beerDTO);
}
