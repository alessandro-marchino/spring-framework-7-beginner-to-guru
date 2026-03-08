package guru.springframework.spring7restmvcapi.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerOrderShipmentDTO.BeerOrderShipmentDTOBuilder.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderShipmentDTO {

	private UUID id;
	@NotBlank
	private String trackingNumber;
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
