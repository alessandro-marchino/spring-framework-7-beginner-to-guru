package guru.springframework.spring7restmvc.events;

import org.springframework.security.core.Authentication;

import guru.springframework.spring7restmvc.entities.Beer;

public interface BeerEvent {
	Beer getBeer();
	Authentication getAuthentication();
	String getEventType();
}
