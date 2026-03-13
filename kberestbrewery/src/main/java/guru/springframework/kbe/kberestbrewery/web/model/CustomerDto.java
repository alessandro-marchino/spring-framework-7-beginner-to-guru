package guru.springframework.kbe.kberestbrewery.web.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

	private UUID id;

	@NotBlank
	@Size(min = 3, max = 100)
	private String name;
}
