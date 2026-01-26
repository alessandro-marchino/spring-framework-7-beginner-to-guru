package guru.springframework.spring7resttemplate.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;

@SpringBootTest
class BeerClientImplTest {

	@Autowired BeerClientImpl beerClientImpl;

    @Test
    void testListBeersNoName() {
		Page<BeerDTO> page = beerClientImpl.listBeers(null, null, null, null, null);
		assertThat(page).isNotNull();
    }

	@Test
    void testListBeersNoParam() {
		Page<BeerDTO> page = beerClientImpl.listBeers();
		assertThat(page).isNotNull();
    }

	@Test
    void testListBeersPageable() {
		Page<BeerDTO> page = beerClientImpl.listBeers(2, 10);
		assertThat(page).isNotNull();
		assertThat(page.getSize()).isEqualTo(10);
    }

	@Test
    void testListBeers() {
		Page<BeerDTO> page = beerClientImpl.listBeers("A", BeerStyle.PORTER, true, 1, 10);
		assertThat(page).isNotNull();
		assertThat(page.getTotalPages()).isLessThan(300);
    }
}
