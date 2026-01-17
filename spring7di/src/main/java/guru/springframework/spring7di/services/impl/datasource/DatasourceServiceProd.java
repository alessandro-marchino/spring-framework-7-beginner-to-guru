package guru.springframework.spring7di.services.impl.datasource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.DatasourceService;

@Profile("prod")
@Service
public class DatasourceServiceProd implements DatasourceService {

	@Override
	public String getDatasource() {
			return "prod-datasource";
	}
}
