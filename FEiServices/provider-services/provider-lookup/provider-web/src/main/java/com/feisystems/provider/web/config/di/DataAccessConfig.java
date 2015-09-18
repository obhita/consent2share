package com.feisystems.provider.web.config.di;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.feisystems.provider.domain.Provider;
import com.feisystems.provider.domain.repository.RepositoryBasePackageMarker;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackageClasses = {RepositoryBasePackageMarker.class} )
public class DataAccessConfig{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${database.driverClassName}")
	private String databaseDriverClassName;

	@Value("${database.url}")
	private String databaseUrl;

	@Value("${database.username}")
	private String databaseUsername;

	@Value("${database.password}")
	private String databasePassword;

	@Bean
	public DataSource dataSource(){
		BasicDataSource dataSource =new BasicDataSource();

		logger.debug("database.driverClassName " + databaseDriverClassName);
		logger.debug("database.url " + databaseUrl);
		logger.debug("database.username " + databaseUsername);

		dataSource.setDriverClassName(databaseDriverClassName);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(1800000);
		dataSource.setNumTestsPerEvictionRun(3);
		dataSource.setMinEvictableIdleTimeMillis(1800000);
		dataSource.setValidationQuery("SELECT 1");
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan("com.feisystems.provider.domain");
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		java.util.Map<String, Object> jpaPropertyMap = entityManagerFactory
				.getJpaPropertyMap();
		jpaPropertyMap.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5InnoDBDialect");
		jpaPropertyMap.put("hibernate.max_fetch_depth", 3);
		jpaPropertyMap.put("hibernate.jdbc.fetch_size", 50);
		jpaPropertyMap.put("hibernate.jdbc.batch_size", 10);
		jpaPropertyMap.put("hibernate.show_sql", true);
		entityManagerFactory.setJpaPropertyMap(jpaPropertyMap);
		return entityManagerFactory;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
		return transactionManager;

	}

	@Bean
	public SessionFactory sessionFactory() throws IOException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setAnnotatedClasses(new Class[] { Provider.class });
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.format_sql", true);
		hibernateProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5InnoDBDialect");
		sessionFactory.setHibernateProperties(hibernateProperties);
		sessionFactory.setDataSource(dataSource());
		sessionFactory.afterPropertiesSet();
		return sessionFactory.getObject();
	}
}
