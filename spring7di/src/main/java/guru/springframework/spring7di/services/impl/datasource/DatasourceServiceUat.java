package guru.springframework.spring7di.services.impl.datasource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.DatasourceService;

@Profile("uat")
@Service
public class DatasourceServiceUat implements DatasourceService {

	@Override
	public String getDatasource() {
			return "uat-datasource";
	}
}
