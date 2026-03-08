package guru.springframework.spring7restmvcapi.events;

import guru.springframework.spring7restmvcapi.model.BeerOrderLineDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkRequestEvent {
	private BeerOrderLineDTO beerOrderLine;
}
