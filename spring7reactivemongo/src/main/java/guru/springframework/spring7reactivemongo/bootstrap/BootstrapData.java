package guru.springframework.spring7reactivemongo.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring7reactivemongo.domain.Beer;
import guru.springframework.spring7reactivemongo.domain.Customer;
import guru.springframework.spring7reactivemongo.repositories.BeerRepository;
import guru.springframework.spring7reactivemongo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		beerRepository.deleteAll().block();
		customerRepository.deleteAll().block();
		loadBeerData().block();
		loadCustomerData().block();

		beerRepository.count().doOnSuccess(count -> log.info("Saved {} beers", count)).block();
		customerRepository.count().doOnSuccess(count -> log.info("Saved {} customers", count)).block();
		log.info("Data loaded");
	}

	private Mono<Void> loadBeerData() {
		return beerRepository.count()
			.flatMapMany(count -> {
				if(count > 0) {
					log.info("Beer data already loaded");
					return Flux.empty();
				}
				log.info("Loading Beer data...");
				return Flux.just(
					Beer.builder()
						.beerName("Galaxy Cat")
						.beerStyle("Pale Ale")
						.upc("123456")
						.price(new BigDecimal("12.99"))
						.quantityOnHand(122)
						.build(),
					Beer.builder()
						.beerName("Crank")
						.beerStyle("Pale Ale")
						.upc("123456222")
						.price(new BigDecimal("11.99"))
						.quantityOnHand(392)
						.build(),
					Beer.builder()
						.beerName("Sunshine City")
						.beerStyle("IPA")
						.upc("12356")
						.price(new BigDecimal("13.99"))
						.quantityOnHand(144)
						.build())
					.flatMap(beerRepository::save);
			})
			.then();
	}

	private Mono<Void> loadCustomerData() {
		return customerRepository.count()
			.flatMapMany(count -> {
				if(count > 0) {
					log.info("Customer data already loaded");
					return Flux.empty();
				}
				log.info("Loading Customer data...");
				return Flux.just(
					Customer.builder()
						.customerName("Sam")
						.build(),
					Customer.builder()
						.customerName("Mike")
						.build(),
					Customer.builder()
						.customerName("Roger")
						.build())
				.flatMap(customerRepository::save);
			})
			.then();
	}
}
