package guru.springframework.spring7restmvc.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.BeerStyle;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

	@Id
	private UUID id;
	@Version
	private Integer version;
	private String beerName;
	private BeerStyle beerStyle;
	private String upc;
	private Integer quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
