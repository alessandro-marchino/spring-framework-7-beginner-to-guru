package guru.springframework.spring7reactivemongo.mapper;

import org.mapstruct.Mapper;

import guru.springframework.spring7reactivemongo.domain.Beer;
import guru.springframework.spring7reactivemongo.model.BeerDTO;

@Mapper
public interface BeerMapper {

	Beer beerDtoToBeer(BeerDTO beerDTO);
	BeerDTO beerToBeerDTO(Beer beer);
}
