package guru.springframework.spring7reactive.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Integer id;
	@NotBlank
	@Size(min = 3, max = 255)
	private String customerName;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
