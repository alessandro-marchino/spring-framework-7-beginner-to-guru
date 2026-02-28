package guru.springframework.spring7restmvc.model;

import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonDeserialize(builder = BeerOrderLineCreateDTO.BeerOrderLineCreateDTOBuilder.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderLineCreateDTO {
	@NotNull
	private UUID beerId;
	@Positive(message = "Quantity on hand must be greater than zero")
	private Integer orderQuantity;
}
