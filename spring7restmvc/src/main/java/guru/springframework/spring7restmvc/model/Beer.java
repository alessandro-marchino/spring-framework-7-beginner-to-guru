package guru.springframework.spring7restmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Beer.BeerBuilder.class)
@Data
@Builder
public class Beer {
	@JsonProperty("id")
	private UUID id;
	@JsonProperty("version")
	private Integer version;
	@JsonProperty("beerName")
	private String beerName;
	@JsonProperty("beerStyle")
	private BeerStyle beerStyle;
	@JsonProperty("upc")
	private String upc;
	@JsonProperty("quantityOnHand")
	private Integer quantityOnHand;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("createdDate")
	private LocalDateTime createdDate;
	@JsonProperty("updatedDate")
	private LocalDateTime updatedDate;
}
