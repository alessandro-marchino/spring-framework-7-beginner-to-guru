package guru.springframework.spring7di.controllers;

import org.springframework.stereotype.Controller;

import guru.springframework.spring7di.services.DatasourceService;

@Controller
public class DatasourceController {

	private final DatasourceService datasourceService;

	/**
	 * @param datasourceService
	 */
	public DatasourceController(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}

	public String getDatasource() {
		return datasourceService.getDatasource();
	}
}
