package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.from;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;

@SpringBootTest
public class BeerControllerTest {
	@Autowired BeerController controller;

	@Test
	void testGetBeerById() {
		UUID uuid = UUID.randomUUID();
		Beer beer = controller.getBeerById(uuid);
		assertThat(beer)
			.isInstanceOf(Beer.class)
			.returns(1, from(Beer::getVersion))
			.returns("Galaxy Cat", from(Beer::getBeerName))
			.returns(BeerStyle.PALE_ALE, from(Beer::getBeerStyle))
			.returns("123456", from(Beer::getUpc))
			.returns(122, from(Beer::getQuantityOnHand))
			.doesNotReturn(null, from(Beer::getCreatedDate))
			.doesNotReturn(null, from(Beer::getUpdatedDate));
	}
}
