package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {

	Beer beerDtoToBeer(BeerDTO dto);
	BeerDTO beerToBeerDto(Beer beer);
}
