package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.model.BeerDTO;
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

	@Override
	public Optional<BeerDTO> getBeerById(UUID beerId) {
		return beerRepository.findById(beerId)
			.map(beerMapper::beerToBeerDto);
	}

	@Override
	public List<BeerDTO> listBeers() {
		return beerRepository.findAll()
			.stream()
			.map(beerMapper::beerToBeerDto)
			.toList();
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {
		LocalDateTime now = LocalDateTime.now();
		beer.setCreatedDate(now);
		beer.setCreatedDate(now);
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
		beerRepository.findById(beerId).ifPresentOrElse(beer -> {
			beer.setBeerName(dto.getBeerName());
			beer.setBeerStyle(dto.getBeerStyle());
			beer.setUpc(dto.getUpc());
			beer.setPrice(dto.getPrice());
			reference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(beer))));
		}, () -> reference.set(Optional.empty()));
		return reference.get();
	}

	@Override
	public void deleteById(UUID beerId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
	}

	@Override
	public void patchBeerById(UUID beerId, BeerDTO beer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'patchBeerById'");
	}

}
