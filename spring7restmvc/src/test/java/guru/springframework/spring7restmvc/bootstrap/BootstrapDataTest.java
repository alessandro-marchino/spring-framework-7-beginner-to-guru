package guru.springframework.spring7restmvc.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import guru.springframework.spring7restmvc.service.BeerCsvService;

@DataJpaTest
public class BootstrapDataTest {
	@Autowired BeerRepository beerRepository;
	@Autowired CustomerRepository customerRepository;
	@MockitoBean BeerCsvService beerCsvService;
	BootstrapData bootstrapData;

	@BeforeEach
	void setUp() {
		bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
	}

	@Test
	void run() throws Exception {
		bootstrapData.run();
		assertThat(beerRepository.count()).isEqualTo(3);
		assertThat(beerRepository.count()).isEqualTo(3);
	}
}
