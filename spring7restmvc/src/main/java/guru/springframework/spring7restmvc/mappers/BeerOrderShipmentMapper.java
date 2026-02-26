package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import guru.springframework.spring7restmvc.entities.BeerOrderShipment;
import guru.springframework.spring7restmvc.entities.BeerOrderShipment_;
import guru.springframework.spring7restmvc.model.BeerOrderShipmentDTO;

@Mapper
public interface BeerOrderShipmentMapper {

	@Mapping(target = BeerOrderShipment_.BEER_ORDER, ignore = true)
	BeerOrderShipment dtoToEntity(BeerOrderShipmentDTO dto);
	BeerOrderShipmentDTO entityToDto(BeerOrderShipment entity);
}
