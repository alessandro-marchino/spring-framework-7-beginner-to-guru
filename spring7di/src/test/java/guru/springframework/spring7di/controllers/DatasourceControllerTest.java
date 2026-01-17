package guru.springframework.spring7di.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

public class DatasourceControllerTest {

	@Nested
	@ActiveProfiles("default")
	@SpringBootTest
	public class DatasourceControllerDefaultTest {
		@Autowired DatasourceController controller;
		@Test
		void testDatasource() {
			assertEquals("dev-datasource", controller.getDatasource());
		}
	}

	@Nested
	@ActiveProfiles("dev")
	@SpringBootTest
	public class DatasourceControllerDevTest {
		@Autowired DatasourceController controller;
		@Test
		void testDatasource() {
			assertEquals("dev-datasource", controller.getDatasource());
		}
	}

	@Nested
	@ActiveProfiles("qa")
	@SpringBootTest
	public class DatasourceControllerQaTest {
		@Autowired DatasourceController controller;
		@Test
		void testDatasource() {
			assertEquals("qa-datasource", controller.getDatasource());
		}
	}

	@Nested
	@ActiveProfiles("uat")
	@SpringBootTest
	public class DatasourceControllerUatTest {
		@Autowired DatasourceController controller;
		@Test
		void testDatasource() {
			assertEquals("uat-datasource", controller.getDatasource());
		}
	}

	@Nested
	@ActiveProfiles("prod")
	@SpringBootTest
	public class DatasourceControllerProdTest {
		@Autowired DatasourceController controller;
		@Test
		void testDatasource() {
			assertEquals("prod-datasource", controller.getDatasource());
		}
	}
}
