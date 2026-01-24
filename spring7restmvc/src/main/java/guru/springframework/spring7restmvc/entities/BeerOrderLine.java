package guru.springframework.spring7restmvc.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private Integer orderQuantity;
	private Integer quantityAllocated;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime lastModifiedDate;
}
