package guru.springframework.spring7restmvc.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import guru.springframework.spring7restmvc.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
@EntityListeners(AuditingEntityListener.class)
public class Beer {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Version
	private Integer version;

	@NotNull
	@NotBlank
	@Size(max = 50)
	@Column(length = 50)
	private String beerName;
	@NotNull
	private BeerStyle beerStyle;
	@NotNull
	@NotBlank
	@Size(max = 255)
	private String upc;
	@PositiveOrZero
	private Integer quantityOnHand;
	@NotNull
	@Positive
	private BigDecimal price;

	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
