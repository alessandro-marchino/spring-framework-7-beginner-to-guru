package guru.springframework.spring7restmvc.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import guru.springframework.spring7restmvc.model.BeerOrderLineStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Positive;
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
public class BeerOrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID id;
	@Version
	private Integer version;

	@ManyToOne
	private Beer beer;
	@ManyToOne
	private BeerOrder beerOrder;
	@Positive(message = "Quantity on hand must be greater than zero")
	@Builder.Default
	private Integer orderQuantity = 1;
	@Builder.Default
	private Integer quantityAllocated = 0;
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private BeerOrderLineStatus orderLineStatus = BeerOrderLineStatus.NEW;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime lastModifiedDate;
}
