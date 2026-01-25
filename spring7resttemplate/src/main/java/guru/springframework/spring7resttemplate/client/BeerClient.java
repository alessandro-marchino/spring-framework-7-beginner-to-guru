package guru.springframework.spring7resttemplate.client;

import org.springframework.data.domain.Page;

import guru.springframework.spring7resttemplate.model.BeerDTO;

public interface BeerClient {

	Page<BeerDTO> listBeers();
}
