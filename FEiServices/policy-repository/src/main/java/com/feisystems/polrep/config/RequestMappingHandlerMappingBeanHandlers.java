package com.feisystems.polrep.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class RequestMappingHandlerMappingBeanHandlers {

	private final static Logger logger = LoggerFactory
			.getLogger(RequestMappingHandlerMappingBeanHandlers.class);

	public static void setRemoveSemicolonContent(
			Object requestMappingHandlerMapping, String beanName) {
		logger.info(
				"Setting 'RemoveSemicolonContent' on 'RequestMappingHandlerMapping'-bean to false. Bean name: {}",
				beanName);
		((RequestMappingHandlerMapping) requestMappingHandlerMapping)
				.setRemoveSemicolonContent(false);
	}

	public static void setUseSuffixPatternMatch(
			Object requestMappingHandlerMapping, String beanName) {
		logger.info(
				"Setting 'UseSuffixPatternMatch' on 'RequestMappingHandlerMapping'-bean to false. Bean name: {}",
				beanName);
		((RequestMappingHandlerMapping) requestMappingHandlerMapping)
				.setUseSuffixPatternMatch(false);
	}
}
