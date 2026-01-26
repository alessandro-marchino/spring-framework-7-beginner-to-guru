package guru.springframework.spring7restmvc.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.BeerCsvRecord;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerCsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;

	private final BeerCsvService beerCsvService;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCsvData();
		loadCustomerData();
		log.info("Data loaded - application ready");
	}

	private void loadCsvData() throws FileNotFoundException {
		if(beerRepository.count() > 10) {
			log.info("Beer data already loaded");
			return;
		}
		File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
		List<BeerCsvRecord> recs = beerCsvService.convertCSV(file);
		recs.forEach(beerCSVRecord -> {
			BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
				case "American Pale Lager" -> BeerStyle.LAGER;
				case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" -> BeerStyle.ALE;
				case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
				case "American Porter" -> BeerStyle.PORTER;
				case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
				case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
				case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
				case "English Pale Ale" -> BeerStyle.PALE_ALE;
				default -> BeerStyle.PILSNER;
			};
			beerRepository.save(Beer.builder()
				.beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
				.beerStyle(beerStyle)
				.upc(beerCSVRecord.getRow().toString())
				.price(BigDecimal.TEN)
				.quantityOnHand(beerCSVRecord.getCount())
				.build());
		});
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
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build());
		customerRepository.save(Customer.builder()
			.customerName("Customer 2")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build());
		customerRepository.save(Customer.builder()
			.customerName("Customer 3")
			.createdDate(LocalDateTime.now())
			.updatedDate(LocalDateTime.now())
			.build());
		customerRepository.flush();
	}
}
