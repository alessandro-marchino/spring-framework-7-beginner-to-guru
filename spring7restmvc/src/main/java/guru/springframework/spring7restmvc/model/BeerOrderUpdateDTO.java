package guru.springframework.spring7restmvc.model;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderUpdateDTO {
	private String customerRef;
	@NotNull
	private UUID customerId;
	private Set<@Valid BeerOrderLineUpdateDTO> beerOrderLines;
	@Valid
	private BeerOrderShipmentUpdateDTO beerOrderShipment;
}
