package guru.springframework.kbe.kberestbrewery.web.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.kbe.kberestbrewery.service.BeerService;
import guru.springframework.kbe.kberestbrewery.web.model.BeerDto;
import guru.springframework.kbe.kberestbrewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

	private static final Integer DEFAULT_PAGE_NUMBER = 0;
	private static final Integer DEFAULT_PAGE_SIZE = 25;

	private final BeerService beerService;

	@GetMapping(produces = { "application/json" }, path = "beer")
	public ResponseEntity<Page<BeerDto>> listBeers(@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) String beerName,
			@RequestParam(required = false) BeerStyleEnum beerStyle,
			@RequestParam(required = false) Boolean showInventoryOnHand) {

		if (showInventoryOnHand == null) {
			showInventoryOnHand = false;
		}

		if (pageNumber == null || pageNumber < 0) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}

		if (pageSize == null || pageSize < 1) {
			pageSize = DEFAULT_PAGE_SIZE;
		}

		Page<BeerDto> beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
		return new ResponseEntity<>(beerList, HttpStatus.OK);
	}

	@GetMapping(path = "beer/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId, @RequestParam(required = false) Boolean showInventoryOnHand) {
		if (showInventoryOnHand == null) {
			showInventoryOnHand = false;
		}

		BeerDto dto = beerService.getById(beerId, showInventoryOnHand);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(path = "beerUpc/{upc}")
	public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable String upc) {
		return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
	}

	@PostMapping(path = "beer")
	public ResponseEntity<Void> saveNewBeer(@RequestBody @Validated BeerDto beerDto) {

		BeerDto savedBeer = beerService.saveNewBeer(beerDto);

		return ResponseEntity
			.created(UriComponentsBuilder
				.fromUriString("http://api.springframework.guru/api/v1/beer/" + savedBeer.getId().toString())
				.build().toUri())
			.build();
	}

	@PutMapping("beer/{beerId}")
	public ResponseEntity<BeerDto> updateBeerById(@PathVariable UUID beerId, @RequestBody @Validated BeerDto beerDto) {
		return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("beer/{beerId}")
	public ResponseEntity<Void> deleteBeerById(@PathVariable UUID beerId) {
		beerService.deleteBeerById(beerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
