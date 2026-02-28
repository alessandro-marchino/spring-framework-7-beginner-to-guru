package guru.springframework.spring7restmvc.mappers;

import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.entities.BeerOrderShipment;
import guru.springframework.spring7restmvc.model.BeerOrderShipmentDTO;

@Mapper
public interface BeerOrderShipmentMapper {

	BeerOrderShipment dtoToEntity(BeerOrderShipmentDTO dto);
	BeerOrderShipmentDTO entityToDto(BeerOrderShipment entity);
}
