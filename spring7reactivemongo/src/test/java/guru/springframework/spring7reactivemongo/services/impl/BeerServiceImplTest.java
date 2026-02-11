package guru.springframework.spring7reactivemongo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7reactivemongo.mapper.BeerMapper;
import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.repositories.BeerRepository;
import guru.springframework.spring7reactivemongo.services.BeerService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class BeerServiceImplTest {

	@Autowired
	BeerService beerService;
	@Autowired
	BeerMapper beerMapper;
	@Autowired
	BeerRepository beerRepository;

	@Test
	void saveBeer() {
		Mono<BeerDTO> savedMono = beerService.saveBeer(Mono.just(getTestBeer()));

		StepVerifier.create(savedMono)
				.assertNext(savedDto -> {
					assertThat(savedDto.getId()).isNotNull();
					assertThat(savedDto.getBeerName()).isEqualTo("Space Dust");
				})
				.verifyComplete();
	}

	@Test
	void testSaveBeerUseBlock() {
		BeerDTO savedDto = beerService.saveBeer(Mono.just(getTestBeer())).block();
		assertThat(savedDto).isNotNull();
		assertThat(savedDto.getId()).isNotNull();
	}

	@Test
	void testUpdateBlocking() {
		final String newName = "New Beer Name";
		BeerDTO savedBeerDto = getSavedBeer();
		savedBeerDto.setBeerName(newName);

		BeerDTO updatedDto = beerService.saveBeer(Mono.just(savedBeerDto)).block();

		// verify exists in db
		BeerDTO fetchedDto = beerService.getById(updatedDto.getId()).block();
		assertThat(fetchedDto.getBeerName()).isEqualTo(newName);
	}

	@Test
	void testUpdateStreaming() {
		final String newName = "New Beer Name";

		AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

		beerService.saveBeer(Mono.just(getTestBeer()))
				.map(savedBeerDto -> {
					savedBeerDto.setBeerName(newName);
					return savedBeerDto;
				})
				.flatMap(savedBeerDto -> beerService.updateBeer(savedBeerDto.getId(), savedBeerDto))
				.flatMap(savedUpdatedDto -> beerService.getById(savedUpdatedDto.getId()))
				.subscribe(dtoFromDb -> atomicDto.set(dtoFromDb));

		await().until(() -> atomicDto.get() != null);
		assertThat(atomicDto.get().getBeerName()).isEqualTo(newName);
	}

	@Test
	void testDeleteBeer() {
		BeerDTO beerToDelete = getSavedBeer();
		beerService.deleteBeer(beerToDelete.getId()).block();
		BeerDTO emptyBeer = beerService.getById(beerToDelete.getId()).block();
		assertThat(emptyBeer).isNull();

	}

	private BeerDTO getSavedBeer() {
		return beerService.saveBeer(Mono.just(getTestBeer())).block();
	}

	private BeerDTO getTestBeer() {
		return BeerDTO.builder()
			.beerName("Space Dust")
			.beerStyle("IPA")
			.price(BigDecimal.TEN)
			.quantityOnHand(12)
			.upc("123123")
			.build();
	}
}
