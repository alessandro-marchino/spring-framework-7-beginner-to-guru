package guru.springframework.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerOrderLineDTO.BeerOrderLineDTOBuilder.class)
@Data
@Builder
public class BeerOrderLineDTO {
	private UUID id;
	private Integer version;
	private BeerDTO beer;
	@Positive(message = "Quantity on hand must be greater than zero")
	private Integer orderQuantity;
	private Integer quantityAllocated;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

}
