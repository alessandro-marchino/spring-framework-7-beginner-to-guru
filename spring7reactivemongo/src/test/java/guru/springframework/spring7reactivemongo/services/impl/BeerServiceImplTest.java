package guru.springframework.spring7reactivemongo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7reactivemongo.mapper.BeerMapper;
import guru.springframework.spring7reactivemongo.model.BeerDTO;
import guru.springframework.spring7reactivemongo.services.BeerService;
import reactor.core.publisher.Mono;

@SpringBootTest
class BeerServiceImplTest {

	@Autowired BeerService beerService;
	@Autowired BeerMapper beerMapper;

	@Test
	void saveBeer() {
		Mono<BeerDTO> savedMono = beerService.saveBeer(Mono.just(getTestBeer()));

		savedMono.subscribe(savedDto -> {
			assertThat(savedDto.getId()).isNotNull();
			assertThat(savedDto.getBeerName()).isEqualTo("Space Dust");
		});
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
