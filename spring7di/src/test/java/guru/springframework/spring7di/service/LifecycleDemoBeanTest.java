package guru.springframework.spring7di.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring7di.services.impl.LifecycleDemoBean;

@SpringBootTest
public class LifecycleDemoBeanTest {

	@Autowired LifecycleDemoBean bean;

	@Test
	void lifecycleTest() {
		assertNotNull(bean.getJavaVer());
	}
}
