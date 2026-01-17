package guru.springframework.spring7restmvc.service;

import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;

public interface BeerService {

	Beer getBeerById(UUID id);
}
