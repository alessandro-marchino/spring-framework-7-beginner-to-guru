package guru.springframework.spring7di.services.impl;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class LifecycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(LifecycleDemoBean.class);
	private String javaVer;

	public LifecycleDemoBean() {
		LOG.info("## I'm in the LicecycleDemoBean constructor ##");
	}

	@Value("${java.specification.version}")
	public void setJavaVer(String javaVer) {
			this.javaVer = javaVer;
			LOG.info("## 1 Properties Set. Java Ver: {}", this.javaVer);
	}
	public String getJavaVer() {
			return javaVer;
	}

	@Override
	public void setBeanName(String name) {
		LOG.info("## 2 BeanNameAware. My Bean name is {}", name);
	}
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		LOG.info("## 3 BeanFactoryAware. BeanFactory has been set");
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOG.info("## 4 ApplicationContextAware. ApplicationContext has been set");
	}
	@PostConstruct
	public void postConstruct() {
		LOG.info("## 5 PostConstruct. The postConstruct method has been called");
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("## 6 InitializingBean. The afterPropertiesSet method has been called");
	}

	@PreDestroy
	public void preDestroy() {
		LOG.info("## 7 PreDestroy. The preDestroy method has been called");
	}
	@Override
	public void destroy() throws Exception {
		LOG.info("## 8 DisposableBean. The destroy method has been called");
	}

	@Override
	public @Nullable Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		LOG.info("## postProcessBeforeInitialization: {}", beanName);
		if(bean instanceof LifecycleDemoBean demo) {
			LOG.info("Calling beforeInit");
			demo.beforeInit();
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	@Override
	public @Nullable Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		LOG.info("## postProcessAfterInitialization: {}", beanName);
		if(bean instanceof LifecycleDemoBean demo) {
			LOG.info("Calling afterInit");
			demo.afterInit();
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	public void beforeInit() {
		LOG.info("## - Before Init - Called by BeanPostProcessor");
	}
	public void afterInit() {
		LOG.info("## - After Init - Called by BeanPostProcessor");
	}
}
