package guru.springframework.spring7restmvc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.service.BeerService;

public class BeerServiceImpl implements BeerService {

	@Override
	public Beer getBeerById(UUID id) {
			return Beer.builder()
				.id(id)
				.version(1)
				.beerName("Galaxy Cat")
				.beerStyle(BeerStyle.PALE_ALE)
				.upc("123456")
				.price(new BigDecimal("12.99"))
				.quantityOnHand(122)
				.createdDate(LocalDateTime.now())
				.updatedDate(LocalDateTime.now())
				.build();
	}
}
