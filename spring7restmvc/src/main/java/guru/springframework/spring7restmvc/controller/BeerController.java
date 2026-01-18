package guru.springframework.spring7restmvc.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7restmvc.model.BeerDTO;
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
	public List<BeerDTO> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping(PATH_ID)
	public BeerDTO getBeerById(@PathVariable("beerId") UUID id) {
		log.debug("Get Beer Id in controller is called with id {}", id);
		return beerService.getBeerById(id)
			.orElseThrow(NotFoundException::new);
	}

	@PostMapping(PATH)
	public ResponseEntity<Void> saveBeer(@RequestBody BeerDTO beer) {
		beer.setId(null);
		beer.setVersion(null);
		BeerDTO savedBeer = beerService.saveNewBeer(beer);
		log.debug("Saved beer with id {}", savedBeer.getId());
		return ResponseEntity
			.created(URI.create("/api/v1/beer/" + savedBeer.getId()))
			.build();
	}

	@PutMapping(PATH_ID)
	public ResponseEntity<Void> updateBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {
		beerService.updateBeerById(id, beer).orElseThrow(NotFoundException::new);
		log.debug("Updated beer with id {}", id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(PATH_ID)
	public ResponseEntity<Void> deleteBeer(@PathVariable("beerId") UUID id) {
		if(!beerService.deleteById(id)) {
			throw new NotFoundException();
		}
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(PATH_ID)
	public ResponseEntity<Void> patchBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {
		beerService.patchBeerById(id, beer).orElseThrow(NotFoundException::new);
		return ResponseEntity.noContent().build();
	}

}
