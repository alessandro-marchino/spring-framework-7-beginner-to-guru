package guru.springframework.spring7restmvc.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerCsvRecord {
	private Integer row;
	private Integer count;
	private String abv;
	private String ibu;
	private Integer id;
	private String beer;
	private String style;
	private Integer breweryId;
	private BigDecimal ounces;
	private String style2;
	private String count_y;
	private String brewery;
	private String city;
	private String state;
	private String label;
}
