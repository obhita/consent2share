package com.feisystems.polrep.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

@Configuration
public class PolRepApplicationConfig implements WebApplicationInitializer {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void onStartup(ServletContext paramServletContext)
			throws ServletException {
		// initialize herasaf context
		SimplePDPFactory.getSimplePDP();
	}
}
