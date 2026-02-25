package guru.springframework.spring7restmvc.events;

import org.springframework.security.core.Authentication;

import guru.springframework.spring7restmvc.entities.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BeerDeletedEvent implements BeerEvent {
	private Beer beer;
	private Authentication authentication;

	@Override
	public String getEventType() {
		return "BEER_DELETED";
	}
}
