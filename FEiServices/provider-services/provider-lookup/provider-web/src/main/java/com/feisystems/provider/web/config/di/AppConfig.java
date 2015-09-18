package com.feisystems.provider.web.config.di;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application context aggregation class.
 *
 * <p>In this application, all beans are registered in the servlet application context
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {AppConfig.class})
public class AppConfig {

}
