package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.BeerOrder;
import guru.springframework.spring7restmvc.entities.BeerOrderShipment;
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
	@Transactional
	void testBeerOrders() {
		BeerOrder beerOrder = BeerOrder.builder()
			.customerRef("Test order")
			.customer(testCustomer)
			.beerOrderShipment(BeerOrderShipment.builder()
				.trackingNumber("1235r")
				.build())
			.build();
		BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
		assertThat(savedBeerOrder.getCustomer().getBeerOrders()).hasSize(2);
		assertThat(savedBeerOrder.getBeerOrderShipment().getId()).isNotNull();
		assertThat(savedBeerOrder.getBeerOrderShipment().getBeerOrder()).isNotNull();
		assertThat(savedBeerOrder.getBeerOrderShipment().getBeerOrder().getId()).isEqualTo(savedBeerOrder.getId());
	}
}

