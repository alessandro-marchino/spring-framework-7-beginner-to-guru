package guru.springframework.spring7restmvc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import guru.springframework.spring7restmvc.model.BeerCsvRecord;
import guru.springframework.spring7restmvc.service.impl.BeerCsvServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BeerCsvServiceImplTest {

	BeerCsvService service = new BeerCsvServiceImpl();

	@Test
	void convertCSV() throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
		List<BeerCsvRecord> recs = service.convertCSV(file);
		log.info("Recs: {}", recs.size());
		assertThat(recs).hasSizeGreaterThan(0);
	}
}
