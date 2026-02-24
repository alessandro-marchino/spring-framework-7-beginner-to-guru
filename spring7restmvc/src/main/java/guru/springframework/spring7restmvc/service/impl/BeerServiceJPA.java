package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Beer_;
import guru.springframework.spring7restmvc.events.BeerCreatedEvent;
import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class BeerServiceJPA implements BeerService {
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	private final CacheManager cacheManager;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Override
	@Cacheable(cacheNames = "beerCache", key = "#beerId")
	public Optional<BeerDTO> getBeerById(UUID beerId) {
		log.info("Get beer by Id - in service - {}", beerId);
		return beerRepository.findById(beerId)
			.map(beerMapper::beerToBeerDto);
	}

	@Override
	@Cacheable(cacheNames = "beerListCache")
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventory, Integer pageNumber, Integer pageSize) {
		log.info("List Beers - in service. beerName: {} - beerStyle: {} - showInventory: {} - pageNumber: {} - pageSize: {}",
			beerName, beerStyle, showInventory, pageNumber, pageSize);

		Beer beerProbe = Beer.builder()
			.beerName(beerName)
			.beerStyle(beerStyle)
			.build();
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
			.withMatcher(Beer_.BEER_NAME, m -> m.ignoreCase().contains());
		Example<Beer> example = Example.of(beerProbe, matcher);
		Sort sort = Sort.by(Sort.Order.asc(Beer_.BEER_NAME));
		return beerRepository.findAll(example, buildPageRequest(pageNumber, pageSize, sort))
			.map(beer -> {
				if(!showInventory) {
					beer.setQuantityOnHand(null);
				}
				return beer;
			})
			.map(beerMapper::beerToBeerDto);
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {
		LocalDateTime now = LocalDateTime.now();
		beer.setCreatedDate(now);
		beer.setCreatedDate(now);
		clearCache(null);

		Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beer));;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		applicationEventPublisher.publishEvent(BeerCreatedEvent.builder()
			.beer(savedBeer)
			.authentication(auth)
			.build());


		return beerMapper.beerToBeerDto(savedBeer);
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO dto) {
		AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
		beerRepository.findById(beerId).ifPresentOrElse(beer -> {
			beer.setBeerName(dto.getBeerName());
			beer.setBeerStyle(dto.getBeerStyle());
			beer.setUpc(dto.getUpc());
			beer.setPrice(dto.getPrice());
			beer.setQuantityOnHand(dto.getQuantityOnHand());
			beer.setUpdatedDate(LocalDateTime.now());
			beer.setVersion(dto.getVersion());
			reference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(beer))));
		}, () -> reference.set(Optional.empty()));
		clearCache(beerId);
		return reference.get();
	}

	@Override
	public boolean deleteById(UUID beerId) {
		if(!beerRepository.existsById(beerId)) {
			return false;
		}
		beerRepository.deleteById(beerId);
		clearCache(beerId);
		return true;
	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
		beerRepository.findById(beerId).ifPresentOrElse(beer -> {
			if(dto.getBeerName() != null) {
				beer.setBeerName(dto.getBeerName());
			}
			if(dto.getBeerStyle() != null) {
				beer.setBeerStyle(dto.getBeerStyle());
			}
			if(dto.getUpc() != null) {
				beer.setUpc(dto.getUpc());
			}
			if(dto.getPrice() != null) {
				beer.setPrice(dto.getPrice());
			}
			if(dto.getQuantityOnHand() != null) {
				beer.setQuantityOnHand(dto.getQuantityOnHand());
			}
			beer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.saveAndFlush(beer))));
		}, () -> reference.set(Optional.empty()));
		clearCache(beerId);
		return reference.get();
	}

	private void clearCache(Object key) {
		Cache beerListCache = cacheManager.getCache("beerListCache");
		if(beerListCache != null) {
			beerListCache.clear();
		}
		if(key != null) {
			Cache beerCache = cacheManager.getCache("beerCache");
			if(beerCache != null) {
				beerCache.evict(key);
			}
		}
	}

}
