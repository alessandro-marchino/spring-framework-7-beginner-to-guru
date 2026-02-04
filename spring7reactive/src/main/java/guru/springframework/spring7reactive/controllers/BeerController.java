package guru.springframework.spring7reactive.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7reactive.model.BeerDTO;
import guru.springframework.spring7reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(BeerController.BEER_PATH)
@RequiredArgsConstructor
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";

	private final BeerService beerService;

	@GetMapping
	public Flux<BeerDTO> listBeers() {
		return beerService.listBeers();
	}
}
