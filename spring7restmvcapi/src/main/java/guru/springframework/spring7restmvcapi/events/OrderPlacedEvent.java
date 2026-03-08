package guru.springframework.spring7restmvcapi.events;

import guru.springframework.spring7restmvcapi.model.BeerOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {
	private BeerOrderDTO beerOrderDTO;
}
