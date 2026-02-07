package guru.springframework.spring7reactive.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

	private Integer id;
	@NotBlank
	@Size(min = 3, max = 255)
	private String beerName;
	@Size(min = 1, max = 255)
	private String beerStyle;
	@Size(max = 25)
	private String upc;
	@PositiveOrZero
	private Integer quantityOnHand;
	@Positive
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
