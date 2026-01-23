package guru.springframework.spring7restmvc.service;

import java.io.File;
import java.util.List;

import guru.springframework.spring7restmvc.model.BeerCsvRecord;

public interface BeerCsvService {

	List<BeerCsvRecord> convertCSV(File file);
}
