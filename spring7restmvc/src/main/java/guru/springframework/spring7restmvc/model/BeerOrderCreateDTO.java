package guru.springframework.spring7restmvc.model;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonDeserialize(builder = BeerOrderCreateDTO.BeerOrderCreateDTOBuilder.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderCreateDTO {
	private String customerRef;
	@NotNull
	private UUID customerId;
	private Set<@Valid BeerOrderLineCreateDTO> beerOrderLines;
}
