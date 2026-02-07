package guru.springframework.spring7reactive.mappers;

import org.mapstruct.Mapper;

import guru.springframework.spring7reactive.domain.Beer;
import guru.springframework.spring7reactive.model.BeerDTO;

@Mapper
public interface BeerMapper {

	BeerDTO toBeerDTO(Beer beer);
	Beer toBeer(BeerDTO beerDTO);
}
