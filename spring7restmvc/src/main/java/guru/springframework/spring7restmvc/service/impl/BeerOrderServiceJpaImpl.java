package guru.springframework.spring7restmvc.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.mappers.BeerOrderMapper;
import guru.springframework.spring7restmvc.model.BeerOrderDTO;
import guru.springframework.spring7restmvc.repositories.BeerOrderRepository;
import guru.springframework.spring7restmvc.service.BeerOrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerOrderServiceJpaImpl implements BeerOrderService {
	private final BeerOrderRepository beerOrderRepository;
	private final BeerOrderMapper beerOrderMapper;

	@Override
	public Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize) {
		if(pageNumber == null || pageNumber < 0) {
			pageNumber = 0;
		}
		if(pageSize == null || pageSize < 1) {
			pageSize = 25;
		}

		return beerOrderRepository.findAll(PageRequest.of(pageNumber, pageSize))
			.map(beerOrderMapper::beerOrderToBeerOrderDto);
	}

	@Override
	public Optional<BeerOrderDTO> getBeerOrderById(UUID id) {
		return beerOrderRepository.findById(id)
			.map(beerOrderMapper::beerOrderToBeerOrderDto);
	}

}
