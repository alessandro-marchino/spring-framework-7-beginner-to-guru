package guru.springframework.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerOrderShipmentDTO.BeerOrderShipmentDTOBuilder.class)
@Data
@Builder
public class BeerOrderShipmentDTO {

	private UUID id;
	@NotBlank
	private String trackingNumber;
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
