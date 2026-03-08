package guru.springframework.spring7restmvcapi.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = CustomerDTO.CustomerDTOBuilder.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
