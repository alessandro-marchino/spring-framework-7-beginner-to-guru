package guru.springframework.spring7restmvc.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import guru.springframework.spring7restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderDTO;
import guru.springframework.spring7restmvc.model.BeerOrderUpdateDTO;

public interface BeerOrderService {

    Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize);

    Optional<BeerOrderDTO> getBeerOrderById(UUID id);

	BeerOrderDTO saveNewBeerOrder(BeerOrderCreateDTO dto);

	BeerOrderDTO updateBeerOrder(UUID id, BeerOrderUpdateDTO dto);

}
