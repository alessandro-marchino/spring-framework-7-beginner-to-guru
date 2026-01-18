package guru.springframework.spring7restmvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.spring7restmvc.service.BeerService;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	@Autowired MockMvc mockMvc;
	@MockitoBean BeerService beerService;

	@Test
	void testGetBeerById() throws Exception {
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
