package guru.springframework.spring7restmvc.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring7restmvc.model.BeerOrderDTO;
import guru.springframework.spring7restmvc.service.BeerOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BeerOrderController {

	public static final String PATH = "/api/v1/beer-order";
	public static final String PATH_ID = PATH + "/{beerOrderId}";
	private final BeerOrderService beerOrderService;

	@GetMapping(PATH)
	public Page<BeerOrderDTO> listBeerOrders(
			@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "1") Integer pageSize) {
		return beerOrderService.listBeerOrders(pageNumber, pageSize);
	}

	@GetMapping(PATH_ID)
	public BeerOrderDTO getBeerById(@PathVariable("beerOrderId") UUID id) {
		log.debug("Get Beer Order Id in controller is called with id {}", id);
		return beerOrderService.getBeerOrderById(id)
			.orElseThrow(NotFoundException::new);
	}

	@PostMapping(PATH)
	public ResponseEntity<Void> createBeerOrder(@RequestBody @Validated BeerOrderCreateDTO dto) {
		BeerOrderDTO savedBeerOrder = beerOrderService.saveNewBeerOrder(dto);
		log.debug("Saved beer order with id {}", savedBeerOrder.getId());
		return ResponseEntity
			.created(UriComponentsBuilder.fromUriString(PATH_ID).build(savedBeerOrder.getId()))
			.build();
	}


}
