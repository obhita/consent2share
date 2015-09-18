package com.feisystems.polrep.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class BeanPostPorcessorConfig implements BeanPostProcessor {

	@SuppressWarnings("rawtypes")
	private final Map<Class, List<BiConsumer<Object, String>>> beanHandlers = new HashMap<Class, List<BiConsumer<Object, String>>>();

	public BeanPostPorcessorConfig() {
		// Register bean handlers by type
		beanHandlers.put(RequestMappingHandlerMapping.class, Arrays.asList(
				RequestMappingHandlerMappingBeanHandlers::setRemoveSemicolonContent,
				RequestMappingHandlerMappingBeanHandlers::setUseSuffixPatternMatch));
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (beanHandlers.keySet().contains(bean.getClass())) {
			beanHandlers.get(bean.getClass()).stream()
					.forEach(handler -> handler.accept(bean, beanName));
		}
		return bean;
	}

}
