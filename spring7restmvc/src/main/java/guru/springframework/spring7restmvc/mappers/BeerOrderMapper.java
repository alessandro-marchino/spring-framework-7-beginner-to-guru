package guru.springframework.spring7restmvc.mappers;


import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.entities.BeerOrder;
import guru.springframework.spring7restmvc.model.BeerOrderDTO;

@Mapper
public interface BeerOrderMapper {

	BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO dto);
	BeerOrderDTO beerOrderToBeerOrderDto(BeerOrder entity);

}
