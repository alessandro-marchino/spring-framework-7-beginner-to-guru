package guru.springframework.spring7restmvcapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerDTO.BeerDTOBuilder.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {
	@JsonProperty("id")
	private UUID id;
	@JsonProperty("version")
	private Integer version;
	@JsonProperty("beerName")
	@NotNull
	@NotBlank
	private String beerName;
	@JsonProperty("beerStyle")
	@NotNull
	private BeerStyle beerStyle;
	@JsonProperty("upc")
	@NotNull
	@NotBlank
	private String upc;
	@JsonProperty("quantityOnHand")
	@PositiveOrZero
	private Integer quantityOnHand;
	@JsonProperty("price")
	@NotNull
	@Positive
	private BigDecimal price;
	@JsonProperty("createdDate")
	private LocalDateTime createdDate;
	@JsonProperty("updatedDate")
	private LocalDateTime updatedDate;
}
