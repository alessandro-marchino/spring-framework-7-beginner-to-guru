package guru.springframework.spring7restmvc.controller;

import org.springframework.stereotype.Controller;

import guru.springframework.spring7restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BeerController {
	private final BeerService beerService;
}
