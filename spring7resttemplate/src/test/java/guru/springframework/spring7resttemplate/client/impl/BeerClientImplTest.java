package guru.springframework.spring7resttemplate.client.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerClientImplTest {

	@Autowired BeerClientImpl beerClientImpl;

    @Test
    void testListBeers() {
		beerClientImpl.listBeers();
    }
}
