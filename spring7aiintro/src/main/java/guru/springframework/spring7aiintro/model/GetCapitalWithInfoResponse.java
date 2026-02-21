package guru.springframework.spring7aiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithInfoResponse(
	@JsonPropertyDescription("This is the city name") String capital,
	@JsonPropertyDescription("This is the population of the city as a whole number") Long population,
	@JsonPropertyDescription("This is the region the city lies in") String region,
	@JsonPropertyDescription("This is the laguage most spoken in the city") String language,
	@JsonPropertyDescription("This is the currency used in the city") String currency
) {
}
