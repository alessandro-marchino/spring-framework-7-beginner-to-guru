package guru.springframework.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Customer.CustomerBuilder.class)
@Data
@Builder
public class Customer {

	private UUID id;
	private Integer version;
	private String customerName;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
