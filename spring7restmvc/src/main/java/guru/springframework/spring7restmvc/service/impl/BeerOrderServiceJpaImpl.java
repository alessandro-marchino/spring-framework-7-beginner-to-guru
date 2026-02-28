package guru.springframework.spring7restmvc.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.controller.NotFoundException;
import guru.springframework.spring7restmvc.entities.BeerOrder;
import guru.springframework.spring7restmvc.entities.BeerOrderLine;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.mappers.BeerOrderMapper;
import guru.springframework.spring7restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderDTO;
import guru.springframework.spring7restmvc.repositories.BeerOrderRepository;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerOrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerOrderServiceJpaImpl implements BeerOrderService {
	private final BeerOrderRepository beerOrderRepository;
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;
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

	@Override
	public BeerOrderDTO saveNewBeerOrder(BeerOrderCreateDTO dto) {
		Customer customer = customerRepository.findById(dto.getCustomerId())
			.orElseThrow(NotFoundException::new);

		Set<BeerOrderLine> beerOrderLines = dto.getBeerOrderLines()
			.stream()
			.map(bol -> BeerOrderLine.builder()
				.beer(beerRepository.findById(bol.getBeerId()).orElseThrow(NotFoundException::new))
				.orderQuantity(bol.getOrderQuantity())
				.build())
			.collect(Collectors.toSet());

		return beerOrderMapper.beerOrderToBeerOrderDto(beerOrderRepository.save(
			BeerOrder.builder()
				.customer(customer)
				.beerOrderLines(beerOrderLines)
				.customerRef(dto.getCustomerRef())
				.build()));
	}
}
