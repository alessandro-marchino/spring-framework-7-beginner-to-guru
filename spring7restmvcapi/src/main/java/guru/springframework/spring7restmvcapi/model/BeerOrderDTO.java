package guru.springframework.spring7restmvcapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerOrderDTO.BeerOrderDTOBuilder.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderDTO {
	private UUID id;
	private Integer version;
	private CustomerDTO customer;
	private String customerRef;
	private BigDecimal paymentAmount;
	private BeerOrderShipmentDTO beerOrderShipment;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	private Set<BeerOrderLineDTO> beerOrderLines;
}
