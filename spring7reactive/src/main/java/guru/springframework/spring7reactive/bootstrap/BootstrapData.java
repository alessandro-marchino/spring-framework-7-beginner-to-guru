package guru.springframework.spring7reactive.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring7reactive.domain.Beer;
import guru.springframework.spring7reactive.domain.Customer;
import guru.springframework.spring7reactive.repositories.BeerRepository;
import guru.springframework.spring7reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class BootstrapData implements CommandLineRunner {
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCustomerData();

		beerRepository.count().subscribe(count -> log.warn("Beer count is: {}", count));
		customerRepository.count().subscribe(count -> log.warn("Customer count is: {}", count));
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

	private void loadCustomerData() {
		customerRepository.count()
			.subscribe(count -> {
				if(count > 0) {
					log.info("Customer data already loaded");
					return;
				}

				log.info("Loading Customer data...");
				customerRepository.save(Customer.builder()
					.customerName("Sam")
					.build()).subscribe();
				customerRepository.save(Customer.builder()
					.customerName("Mike")
					.build()).subscribe();
				customerRepository.save(Customer.builder()
					.customerName("Roger")
					.build()).subscribe();
			});
	}
}
