package guru.springframework.spring7restmvc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

	private Map<UUID, BeerDTO> beerMap;

	public BeerServiceImpl() {
		this.beerMap = new HashMap<>();
		BeerDTO beer1 = BeerDTO.builder()
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
		BeerDTO beer2 = BeerDTO.builder()
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
		BeerDTO beer3 = BeerDTO.builder()
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
	public List<BeerDTO> listBeers() {
		return List.copyOf(beerMap.values());
	}

	@Override
	public Optional<BeerDTO> getBeerById(UUID beerId) {
		log.debug("Get Beer Id in service is called with id {}", beerId);
		return Optional.ofNullable(beerMap.get(beerId));
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {
		BeerDTO savedBeer = BeerDTO.builder()
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
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
		BeerDTO existing = beerMap.get(beerId);
		if(existing == null) {
			return Optional.empty();
		}
		existing.setBeerName(beer.getBeerName());
		existing.setBeerStyle(beer.getBeerStyle());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setUpc(beer.getUpc());
		existing.setPrice(beer.getPrice());
		existing.setUpdatedDate(LocalDateTime.now());
		existing.setVersion(existing.getVersion() + 1);
		beerMap.put(beerId, existing);
		return Optional.of(existing);
	}

	@Override
	public boolean deleteById(UUID beerId) {
		return beerMap.remove(beerId) != null;
	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
		BeerDTO existing = beerMap.get(beerId);
		if(existing == null) {
			return Optional.empty();
		}
		if(beer.getBeerName() != null) {
			existing.setBeerName(beer.getBeerName());
		}
		if(beer.getBeerStyle() != null) {
			existing.setBeerStyle(beer.getBeerStyle());
		}
		if(beer.getQuantityOnHand() != null) {
			existing.setQuantityOnHand(beer.getQuantityOnHand());
		}
		if(beer.getUpc() != null) {
			existing.setUpc(beer.getUpc());
		}
		if(beer.getPrice() != null) {
			existing.setPrice(beer.getPrice());
		}
		existing.setUpdatedDate(LocalDateTime.now());
		existing.setVersion(existing.getVersion() + 1);
		beerMap.put(beerId, existing);
		return Optional.of(existing);
	}
}
