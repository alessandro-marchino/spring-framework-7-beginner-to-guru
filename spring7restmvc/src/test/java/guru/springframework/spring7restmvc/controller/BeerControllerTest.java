package guru.springframework.spring7restmvc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import guru.springframework.spring7restmvc.service.impl.BeerServiceImpl;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	@Autowired MockMvc mockMvc;
	@MockitoBean BeerService beerService;

	@Test
	void testGetBeerById() throws Exception {
		Beer testBeer = new BeerServiceImpl().listBeers().get(0);
		given(beerService.getBeerById(any(UUID.class))).willReturn(testBeer);

		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON));
	}
}
