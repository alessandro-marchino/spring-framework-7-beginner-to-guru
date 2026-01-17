package guru.springframework.spring7di.services.impl.datasource;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.spring7di.services.DatasourceService;

@Profile({ "dev", "default", "EN", "ES" })
@Service
public class DatasourceServiceDev implements DatasourceService {

	@Override
	public String getDatasource() {
			return "dev-datasource";
	}
}
