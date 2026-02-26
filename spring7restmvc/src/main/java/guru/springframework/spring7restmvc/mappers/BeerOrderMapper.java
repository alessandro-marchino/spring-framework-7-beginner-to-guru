package guru.springframework.spring7restmvc.mappers;


import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.entities.BeerOrder;
import guru.springframework.spring7restmvc.model.BeerOdrerDTO;

@Mapper
public interface BeerOrderMapper {

	BeerOrder beerOrderDtoToBeerOrder(BeerOdrerDTO dto);
	BeerOdrerDTO beerOrderToBeerOrderDto(BeerOrder entity);

}
