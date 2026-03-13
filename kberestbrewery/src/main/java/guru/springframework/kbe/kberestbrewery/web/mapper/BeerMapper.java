package guru.springframework.kbe.kberestbrewery.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import guru.springframework.kbe.kberestbrewery.domain.Beer;
import guru.springframework.kbe.kberestbrewery.web.model.BeerDto;

@Mapper
public interface BeerMapper {
	@Mapping(target = "lastUpdatedDate", source = "lastModifiedDate")
	BeerDto beerToBeerDto(Beer beer);

	@Mapping(target = "lastUpdatedDate", source = "lastModifiedDate")
	BeerDto beerToBeerDtoWithInventory(Beer beer);

	@Mapping(target = "lastModifiedDate", source = "lastUpdatedDate")
	Beer beerDtoToBeer(BeerDto dto);
}
