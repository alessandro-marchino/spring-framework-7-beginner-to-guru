package guru.springframework.spring7restmvc.model;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@JsonDeserialize(builder = BeerOrderCreateDTO.BeerOrderCreateDTOBuilder.class)
@Data
@Builder
public class BeerOrderCreateDTO {
	private String customerRef;
	@NotNull
	private UUID customerId;
	private Set<BeerOrderLineCreateDTO> beerOrderLines;
}
