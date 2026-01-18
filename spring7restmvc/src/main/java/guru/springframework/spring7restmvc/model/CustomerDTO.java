package guru.springframework.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = CustomerDTO.CustomerDTOBuilder.class)
@Data
@Builder
public class CustomerDTO {

	@JsonProperty("id")
	private UUID id;
	@JsonProperty("version")
	private Integer version;
	@JsonProperty("customerName")
	private String customerName;
	@JsonProperty("createdDate")
	private LocalDateTime createdDate;
	@JsonProperty("updatedDate")
	private LocalDateTime updatedDate;
}
