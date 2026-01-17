package guru.springframework.spring7restmvc.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BeerController {
	private final BeerService beerService;

	public Beer getBeerById(UUID id) {
		log.debug("Get Beer Id in controller is called with id {}", id);
		return beerService.getBeerById(id);
	}
}
