package guru.springframework.spring7restmvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerOrderShipmentUpdateDTO.BeerOrderShipmentUpdateDTOBuilder.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderShipmentUpdateDTO {
	@NotBlank
	private String trackingNumber;
}
