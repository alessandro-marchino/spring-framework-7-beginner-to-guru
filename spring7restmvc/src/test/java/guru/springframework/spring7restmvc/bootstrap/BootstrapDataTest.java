package guru.springframework.spring7restmvc.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerCsvService;
import guru.springframework.spring7restmvc.service.impl.BeerCsvServiceImpl;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
public class BootstrapDataTest {
	@Autowired BeerRepository beerRepository;
	@Autowired CustomerRepository customerRepository;
	@Autowired BeerCsvService beerCsvService;
	BootstrapData bootstrapData;

	@BeforeEach
	void setUp() {
		bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
	}

	@Test
	void run() throws Exception {
		bootstrapData.run();
		assertThat(beerRepository.count()).isEqualTo(2413);
		assertThat(customerRepository.count()).isEqualTo(3);
	}
}
