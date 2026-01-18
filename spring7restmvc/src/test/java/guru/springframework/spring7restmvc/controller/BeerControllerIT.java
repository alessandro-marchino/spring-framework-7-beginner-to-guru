package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring7restmvc.entities.Beer;
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
    void testGetBeerById() {
		Beer beer = repository.findAll().getFirst();
		BeerDTO dto = controller.getBeerById(beer.getId());

		assertThat(dto).isNotNull();
    }

	@Test
    void testGetBeerByIdNotFound() {
		assertThrows(NotFoundException.class, () -> controller.getBeerById(UUID.randomUUID()));
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
