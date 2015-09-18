package com.feisystems.provider.web.config.di;

import java.util.ArrayList;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.feisystems.provider.service.ServiceBasePackageMarker;

@Configuration
@ComponentScan(basePackageClasses = {ServiceBasePackageMarker.class})
public class ServiceLayerConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public Mapper mapper() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		ArrayList<String> fileLists = new ArrayList<String>();
		fileLists.add("dozer-bean-mappings.xml");
		dozerBeanMapper.setMappingFiles(fileLists);
		return dozerBeanMapper;
	}

}
