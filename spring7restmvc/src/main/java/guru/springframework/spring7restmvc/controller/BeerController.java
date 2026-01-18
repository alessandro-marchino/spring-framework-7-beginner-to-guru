package guru.springframework.spring7restmvc.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BeerController {
	public static final String PATH = "/api/v1/beer";
	public static final String PATH_ID = PATH + "/{beerId}";
	private final BeerService beerService;

	@GetMapping(PATH)
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping(PATH_ID)
	public Beer getBeerById(@PathVariable("beerId") UUID id) {
		log.debug("Get Beer Id in controller is called with id {}", id);
		return beerService.getBeerById(id);
	}

	@PostMapping(PATH)
	public ResponseEntity<Void> saveBeer(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		log.debug("Saved beer with id {}", savedBeer.getId());
		return ResponseEntity
			.created(URI.create("/api/v1/beer/" + savedBeer.getId()))
			.build();
	}

	@PutMapping(PATH_ID)
	public ResponseEntity<Void> updateBeer(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {
		beerService.updateBeerById(id, beer);
		log.debug("Updated beer with id {}", id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(PATH_ID)
	public ResponseEntity<Void> deleteBeer(@PathVariable("beerId") UUID id) {
		beerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(PATH_ID)
	public ResponseEntity<Void> patchBeer(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {
		beerService.patchBeerById(id, beer);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException() {
		return ResponseEntity.notFound().build();
	}
}
