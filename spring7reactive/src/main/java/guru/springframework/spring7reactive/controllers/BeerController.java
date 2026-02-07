package guru.springframework.spring7reactive.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.spring7reactive.model.BeerDTO;
import guru.springframework.spring7reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(BeerController.BEER_PATH)
@RequiredArgsConstructor
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";
	public static final String BEER_PATH_ID = "/{beerId}";

	private final BeerService beerService;

	@GetMapping
	public Flux<BeerDTO> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping(BEER_PATH_ID)
	public Mono<BeerDTO> getBeerById(@PathVariable Integer beerId) {
		return beerService.getBeerById(beerId);
	}

	@PostMapping
	public Mono<ResponseEntity<Void>> createNewBeer(@RequestBody BeerDTO beerDTO) {
		return beerService.saveNewBeer(beerDTO)
			.map(savedDto -> ResponseEntity.created(UriComponentsBuilder.fromUriString("http://localhost:8080" + BEER_PATH + BEER_PATH_ID).build(savedDto.getId())).build());
	}

	@PutMapping(BEER_PATH_ID)
	public Mono<ResponseEntity<Void>> updateExistingBeer(@PathVariable Integer beerId, @RequestBody BeerDTO beerDTO) {
		return beerService.updateBeer(beerId, beerDTO)
			.map(_ -> ResponseEntity.ok().build());
	}
}
