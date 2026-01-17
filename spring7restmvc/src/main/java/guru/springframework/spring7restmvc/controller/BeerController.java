package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BeerController {
	private final BeerService beerService;

	@GetMapping("/api/v1/beer")
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}

	public Beer getBeerById(UUID id) {
		log.debug("Get Beer Id in controller is called with id {}", id);
		return beerService.getBeerById(id);
	}
}
