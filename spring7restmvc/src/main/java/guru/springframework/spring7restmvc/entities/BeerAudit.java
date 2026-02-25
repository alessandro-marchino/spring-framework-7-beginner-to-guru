package guru.springframework.spring7restmvc.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import guru.springframework.spring7restmvc.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class BeerAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID auditId;

	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID id;
	private Integer version;

	@Column(length = 50)
	private String beerName;
	private BeerStyle beerStyle;
	@Size(max = 255)
	private String upc;
	private Integer quantityOnHand;
	private BigDecimal price;

	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDateAudit;

	private String principalName;
	private String auditEventType;

}
