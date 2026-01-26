package guru.springframework.spring7resttemplate.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;

@SpringBootTest
class BeerClientImplTest {

	@Autowired BeerClientImpl beerClient;

    @Test
    void testListBeersNoName() {
		Page<BeerDTO> page = beerClient.listBeers(null, null, null, null, null);
		assertThat(page).isNotNull();
    }

	@Test
    void testListBeersNoParam() {
		Page<BeerDTO> page = beerClient.listBeers();
		assertThat(page).isNotNull();
    }

	@Test
    void testListBeersPageable() {
		Page<BeerDTO> page = beerClient.listBeers(2, 10);
		assertThat(page).isNotNull();
		assertThat(page.getSize()).isEqualTo(10);
    }

	@Test
    void testListBeers() {
		Page<BeerDTO> page = beerClient.listBeers("A", BeerStyle.PORTER, true, 1, 10);
		assertThat(page).isNotNull();
		assertThat(page.getTotalPages()).isLessThan(300);
    }

	@Test
	void testGetBeerById() {
		Page<BeerDTO> page = beerClient.listBeers();
		UUID id = page.getContent().getFirst().getId();
		BeerDTO beerDTO = beerClient.getBeerById(id, Boolean.TRUE);
		assertThat(beerDTO).isNotNull();
		assertThat(beerDTO.getId()).isEqualTo(id);
	}

	@Test
	void testCreateBeer() {
		BeerDTO newDto = BeerDTO.builder()
			.price(new BigDecimal("10.99"))
			.beerName("TEST beer")
			.beerStyle(BeerStyle.IPA)
			.quantityOnHand(500)
			.upc("123245")
			.build();
		BeerDTO savedDTO = beerClient.createBeer(newDto);
		assertThat(savedDTO).isNotNull();
	}
}
