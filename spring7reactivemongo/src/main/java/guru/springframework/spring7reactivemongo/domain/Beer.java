package guru.springframework.spring7reactivemongo.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

	private Integer id;
	private String beerName;
	private String beerStyle;
	private String upc;
	private Integer quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
