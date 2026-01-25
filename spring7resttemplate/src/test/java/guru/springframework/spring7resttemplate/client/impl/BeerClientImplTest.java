package guru.springframework.spring7resttemplate.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import guru.springframework.spring7resttemplate.model.BeerDTO;

@SpringBootTest
class BeerClientImplTest {

	@Autowired BeerClientImpl beerClientImpl;

    @Test
    void testListBeers() {
		Page<BeerDTO> page = beerClientImpl.listBeers();
		assertThat(page).isNotNull();
    }
}
