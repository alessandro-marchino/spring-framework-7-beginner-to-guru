package guru.springframework.spring7reactivemongo.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring7reactivemongo.domain.Beer;
import guru.springframework.spring7reactivemongo.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository beerRepository;

	@Override
	public void run(String... args) throws Exception {
		beerRepository.deleteAll()
			.doOnSuccess(_ -> loadBeerData())
			.subscribe();
	}

	private void loadBeerData() {
		beerRepository.count()
			.subscribe(count -> {
				if(count > 0) {
					log.info("Beer data already loaded");
					return;
				}

				log.info("Loading Beer data...");
				beerRepository.save(Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle("Pale Ale")
					.upc("123456")
					.price(new BigDecimal("12.99"))
					.quantityOnHand(122)
					.build()).subscribe();
				beerRepository.save(Beer.builder()
					.beerName("Crank")
					.beerStyle("Pale Ale")
					.upc("123456222")
					.price(new BigDecimal("11.99"))
					.quantityOnHand(392)
					.build()).subscribe();
				beerRepository.save(Beer.builder()
					.beerName("Sunshine City")
					.beerStyle("IPA")
					.upc("12356")
					.price(new BigDecimal("13.99"))
					.quantityOnHand(144)
					.build()).subscribe();
			});
	}
}
