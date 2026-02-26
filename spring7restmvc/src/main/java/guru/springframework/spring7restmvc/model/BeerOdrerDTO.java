package guru.springframework.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerOdrerDTO.BeerOdrerDTOBuilder.class)
@Data
@Builder
public class BeerOdrerDTO {
	private UUID id;
	private Integer version;
	private CustomerDTO customer;
	private String customerRef;
	private BeerOrderShipmentDTO beerOrderShipment;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	private Set<BeerOrderLineDTO> beerORderLines;
}
