package com.feisystems.provider.web.config.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.feisystems.provider.web.controller.ControllerBasePackageMarker;

/**
 * WebMvcConfigurer implementation defines callback methods to customize the Java-based configuration for Spring MVC enabled via @EnableWebMvc.
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {ControllerBasePackageMarker.class})
public class MvcConfig extends WebMvcConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		logger.debug("configureDefaultServletHandling is called");

		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		logger.debug("addViewControllers is called");

		registry.addViewController("/").setViewName("index");
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/index").setViewName("index");
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		logger.debug("Setting up viewResolver bean");

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
