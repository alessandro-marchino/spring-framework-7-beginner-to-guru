package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.repositories.BeerRepository;

@SpringBootTest
public class BeerControllerIT {
	@Autowired BeerController controller;
	@Autowired BeerRepository repository;

    @Test
	@Disabled
    void testDeleteBeer() {

    }

    @Test
	@Disabled
    void testGetBeerById() {

    }

    @Test
    void testListBeers() {
		List<BeerDTO> dtos = controller.listBeers();
		assertThat(dtos).hasSize(3);
    }

	@Test
	@Transactional
	@Rollback
    void testListBeersEmpty() {
		repository.deleteAll();
		List<BeerDTO> dtos = controller.listBeers();
		assertThat(dtos).hasSize(0);
    }

    @Test
	@Disabled
    void testPatchBeer() {

    }

    @Test
	@Disabled
    void testSaveBeer() {

    }

    @Test
	@Disabled
    void testUpdateBeer() {

    }
}
