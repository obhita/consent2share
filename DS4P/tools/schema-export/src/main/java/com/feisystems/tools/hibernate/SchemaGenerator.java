package com.feisystems.tools.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.springframework.util.Assert;

public class SchemaGenerator {
	private Ejb3Configuration jpaConfiguration;
	private Dialect dialect;
	private Configuration hibernateConfiguration;

	public SchemaGenerator(Ejb3Configuration jpaConfiguration) {
		Assert.notNull(jpaConfiguration, "JpaConfiguration is required.");
		this.jpaConfiguration = jpaConfiguration;

		// This line of code must be called to further do the configuration, so
		// that JSR 303 validation annotations on Entity class can be honored
		jpaConfiguration.buildEntityManagerFactory();

		this.hibernateConfiguration = jpaConfiguration
				.getHibernateConfiguration();

		this.dialect = Dialect.getDialect(hibernateConfiguration
				.getProperties());
	}

	public String[] generateSchemaCreationScript() {

		String[] createSql = hibernateConfiguration
				.generateSchemaCreationScript(dialect);

		return createSql;
	}
	
	public String[] generateDropSchemaScript() {

		String[] dropSql = hibernateConfiguration
				.generateDropSchemaScript(dialect);

		return dropSql;
	}
}
