package guru.springframework.spring7restmvc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

	private Map<UUID, Beer> beerMap;

	public BeerServiceImpl() {
		this.beerMap = new HashMap<>();
		Beer beer1 = Beer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.beerName("Galaxy Cat")
			.beerStyle(BeerStyle.PALE_ALE)
			.upc("123456")
			.price(new BigDecimal("12.99"))
			.quantityOnHand(122)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		Beer beer2 = Beer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.beerName("Crank")
			.beerStyle(BeerStyle.PALE_ALE)
			.upc("123456222")
			.price(new BigDecimal("11.99"))
			.quantityOnHand(392)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		Beer beer3 = Beer.builder()
			.id(UUID.randomUUID())
			.version(1)
			.beerName("Sunshine City")
			.beerStyle(BeerStyle.IPA)
			.upc("12356")
			.price(new BigDecimal("13.99"))
			.quantityOnHand(144)
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build();
		beerMap.put(beer1.getId(), beer1);
		beerMap.put(beer2.getId(), beer2);
		beerMap.put(beer3.getId(), beer3);
	}

	@Override
	public List<Beer> listBeers() {
		return List.copyOf(beerMap.values());
	}

	@Override
	public Beer getBeerById(UUID id) {
		log.debug("Get Beer Id in service is called with id {}", id);
		return beerMap.get(id);
	}

	@Override
	public Beer saveNewBeer(Beer beer) {
		Beer savedBeer = Beer.builder()
			.id(UUID.randomUUID())
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.version(1)
			.beerName(beer.getBeerName())
			.beerStyle(beer.getBeerStyle())
			.quantityOnHand(beer.getQuantityOnHand())
			.upc(beer.getUpc())
			.price(beer.getPrice())
			.build();
		beerMap.put(savedBeer.getId(), savedBeer);
		return savedBeer;
	}

	@Override
	public void updateBeerById(UUID beerId, Beer beer) {
		Beer existing = beerMap.get(beerId);
		if(existing == null) {
			throw new RuntimeException("Beer with id " + beerId + " not found");
		}
		existing.setBeerName(beer.getBeerName());
		existing.setBeerStyle(beer.getBeerStyle());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setUpc(beer.getUpc());
		existing.setPrice(beer.getPrice());
		existing.setUpdatedDate(LocalDateTime.now());
		existing.setVersion(existing.getVersion() + 1);
		beerMap.put(beerId, existing);
	}

	@Override
	public void deleteById(UUID id) {
		beerMap.remove(id);
	}
}
