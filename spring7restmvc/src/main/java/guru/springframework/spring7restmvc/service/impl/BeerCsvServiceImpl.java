package guru.springframework.spring7restmvc.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import guru.springframework.spring7restmvc.model.BeerCsvRecord;
import guru.springframework.spring7restmvc.service.BeerCsvService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeerCsvServiceImpl implements BeerCsvService {

	@Override
	public List<BeerCsvRecord> convertCSV(File file) {
		try {
			List<BeerCsvRecord> beerCsvRecords = new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(file))
				.withType(BeerCsvRecord.class)
				.build()
				.parse();
			return beerCsvRecords;
		} catch(FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
