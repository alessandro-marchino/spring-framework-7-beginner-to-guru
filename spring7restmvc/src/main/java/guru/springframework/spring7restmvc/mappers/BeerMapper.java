package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.BeerAudit;
import guru.springframework.spring7restmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {

	@Mapping(target = "categories", ignore = true)
	@Mapping(target = "beerOrderLines", ignore = true)
	Beer beerDtoToBeer(BeerDTO dto);

	BeerDTO beerToBeerDto(Beer beer);

	@Mapping(target = "createdDateAudit", ignore = true)
	@Mapping(target = "auditId", ignore = true)
	@Mapping(target = "auditEventType", ignore = true)
	@Mapping(target = "principalName", ignore = true)
	BeerAudit beerToBeerAudit(Beer beer);
}
