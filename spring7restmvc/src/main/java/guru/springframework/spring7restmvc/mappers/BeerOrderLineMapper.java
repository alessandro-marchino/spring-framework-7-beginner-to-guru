package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.entities.BeerOrderLine;
import guru.springframework.spring7restmvc.model.BeerOrderLineDTO;

@Mapper
public interface BeerOrderLineMapper {

	BeerOrderLine dtoToEntity(BeerOrderLineDTO dto);
	BeerOrderLineDTO entityToDto(BeerOrderLine entity);
}
