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
import guru.springframework.spring7restmvc.entities.Beer_;
import guru.springframework.spring7restmvc.entities.Category;

@SpringBootTest
public class CategoryRepositoryTest {
	@Autowired CategoryRepository categoryRepository;
	@Autowired BeerRepository beerRepository;
	Beer testBeer;

	@BeforeEach
	void setUp() {
		testBeer = beerRepository.findAll(PageRequest.of(0, 1, Sort.by(Beer_.BEER_NAME))).getContent().getFirst();
	}

	@Test
	@Transactional
	void testAddCategory() {
		Category savedCategory = categoryRepository.save(Category.builder()
			.description("Alas")
			.build());
		testBeer.addCategory(savedCategory);
		Beer savedBeer = beerRepository.save(testBeer);
		assertThat(savedBeer.getCategories()).hasSize(1);
		assertThat(savedCategory.getBeers()).hasSize(1);
	}
}
