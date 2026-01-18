package guru.springframework.spring7restmvc.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCustomerData();
	}

	private void loadBeerData() {
		if(beerRepository.count() > 0) {
			log.info("Beer data already loaded");
			return;
		}
		log.info("Loading Beer data...");
		beerRepository.save(Beer.builder()
			.beerName("Galaxy Cat")
			.beerStyle(BeerStyle.PALE_ALE)
			.upc("123456")
			.price(new BigDecimal("12.99"))
			.quantityOnHand(122)
			.build());
		beerRepository.save(Beer.builder()
			.beerName("Crank")
			.beerStyle(BeerStyle.PALE_ALE)
			.upc("123456222")
			.price(new BigDecimal("11.99"))
			.quantityOnHand(392)
			.build());
		beerRepository.save(Beer.builder()
			.beerName("Sunshine City")
			.beerStyle(BeerStyle.IPA)
			.upc("12356")
			.price(new BigDecimal("13.99"))
			.quantityOnHand(144)
			.build());
		beerRepository.flush();
	}
	private void loadCustomerData() {
		if(customerRepository.count() > 0) {
			log.info("Customer data already loaded");
			return;
		}
		log.info("Loading Customer data...");
		customerRepository.save(Customer.builder()
			.customerName("Customer 1")
			.build());
		customerRepository.save(Customer.builder()
			.customerName("Customer 2")
			.build());
		customerRepository.save(Customer.builder()
			.customerName("Customer 3")
			.build());
		customerRepository.flush();
	}
}
