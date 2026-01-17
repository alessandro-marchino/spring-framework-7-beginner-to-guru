package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/beer")
public class BeerController {
	private final BeerService beerService;

	@GetMapping
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping("/{beerId}")
	public Beer getBeerById(@PathVariable("beerId") UUID id) {
		log.debug("Get Beer Id in controller is called with id {}", id);
		return beerService.getBeerById(id);
	}

	@PostMapping
	public ResponseEntity<Void> handlePost(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		log.debug("Saved beer with id {}", savedBeer.getId());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
