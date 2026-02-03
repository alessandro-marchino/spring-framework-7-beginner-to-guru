package guru.springframework.spring7reactive.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

	@Id
	private Integer id;
	private String beerName;
	private String beerStyle;
	private String upc;
	private Integer quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime lastMOdifiedDate;
}
