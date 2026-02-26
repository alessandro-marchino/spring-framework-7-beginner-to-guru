package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import guru.springframework.spring7restmvc.entities.BeerOrderLine;
import guru.springframework.spring7restmvc.entities.BeerOrderLine_;
import guru.springframework.spring7restmvc.model.BeerOrderLineDTO;

@Mapper
public interface BeerOrderLineMapper {

	@Mapping(target = BeerOrderLine_.BEER_ORDER, ignore = true)
	BeerOrderLine dtoToEntity(BeerOrderLineDTO dto);
	BeerOrderLineDTO entityToDto(BeerOrderLine entity);
}
