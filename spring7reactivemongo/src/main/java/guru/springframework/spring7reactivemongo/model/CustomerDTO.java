package guru.springframework.spring7reactivemongo.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private String id;
	@NotBlank
	private String customerName;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
