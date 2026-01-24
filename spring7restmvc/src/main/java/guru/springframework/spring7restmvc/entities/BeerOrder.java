package guru.springframework.spring7restmvc.entities;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
public class BeerOrder {


	/**
	 * @param id
	 * @param version
	 * @param customer
	 * @param customerRef
	 * @param createdDate
	 * @param lastModifiedDate
	 * @param beerOrderLines
	 */
	public BeerOrder(UUID id, Integer version, Customer customer, String customerRef, BeerOrderShipment beerOrderShipment, LocalDateTime createdDate,
			LocalDateTime lastModifiedDate, Set<BeerOrderLine> beerOrderLines) {
		this.id = id;
		this.version = version;
		this.customerRef = customerRef;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.beerOrderLines = beerOrderLines;
		this.setCustomer(customer);
		this.setBeerOrderShipment(beerOrderShipment);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.CHAR)
	private UUID id;
	@Version
	private Integer version;

	@ManyToOne
	private Customer customer;
	private String customerRef;

	@OneToOne(cascade = CascadeType.PERSIST)
	private BeerOrderShipment beerOrderShipment;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime lastModifiedDate;

	@OneToMany(mappedBy = BeerOrderLine_.BEER_ORDER)
	private Set<BeerOrderLine> beerOrderLines;

	public void setCustomer(Customer customer) {
		this.customer = customer;
		customer.getBeerOrders().add(this);
	}
	public void setBeerOrderShipment(BeerOrderShipment beerOrderShipment) {
		this.beerOrderShipment = beerOrderShipment;
		beerOrderShipment.setBeerOrder(this);
	}
}
