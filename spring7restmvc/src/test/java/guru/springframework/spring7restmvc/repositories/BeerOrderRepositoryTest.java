package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Beer_;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.entities.Customer_;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BeerOrderRepositoryTest {
	@Autowired BeerOrderRepository beerOrderRepository;
	@Autowired CustomerRepository customerRepository;
	@Autowired BeerRepository beerRepository;

	Customer testCustomer;
	Beer testBeer;

	@BeforeEach
	void setUp() {
		testCustomer = customerRepository.findAll(PageRequest.of(0, 1, Sort.by(Customer_.CUSTOMER_NAME))).getContent().getFirst();
		testBeer = beerRepository.findAll(PageRequest.of(0, 1, Sort.by(Beer_.BEER_NAME))).getContent().getFirst();
	}

	@Test
	void testBeerOrders() {
		assertThat(beerOrderRepository.count()).isEqualTo(0);
		assertThat(customerRepository.count()).isEqualTo(3);
		assertThat(beerRepository.count()).isEqualTo(2413);

		log.warn("{}", testCustomer.getCustomerName());
		log.warn("{}", testBeer.getBeerName());
	}
}

