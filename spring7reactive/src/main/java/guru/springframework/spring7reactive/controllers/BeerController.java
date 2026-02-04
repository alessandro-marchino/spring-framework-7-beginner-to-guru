package guru.springframework.spring7reactive.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7reactive.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(BeerController.BEER_PATH)
@RequiredArgsConstructor
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";

	@GetMapping
	public Flux<BeerDTO> listBeers() {
		return Flux.just(
			BeerDTO.builder().id(1).build(),
			BeerDTO.builder().id(2).build()
		);
	}
}
