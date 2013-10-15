package com.feisystems.tools.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;

import java.io.*;

/*
 * Usage example in maven:
 <profile>
	<!-- mvn clean install -Pdb-schema-drop -->
	<id>db-schema-drop</id>
	<build>
		<plugins>
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>exec-maven-plugin</artifactId>
				<version>1.2.1</version>
				<executions>
					<execution>
						<id>generate-create-schema-ddl</id>
						<phase>process-classes</phase>
						<goals>
							<goal>java</goal>
						</goals>
						<configuration>
							<mainClass>com.feisystems.tools.hibernate.SpringEntityManagerFactoryBeanConfigSchemaExporter</mainClass>
							<arguments>
								<argument>--drop</argument>
								<argument>--delimiter=;</argument>
								<argument>--output=${project.basedir}/${project.build.finalName}-drop-tables.sql</argument>
								<!-- spring.profiles.active defined in web.xml -->
								<argument>--activeSpringProfile=runtime</argument>
								<argument>--dataAccessApplicationContextConfigClassPath=classpath:META-INF/spring/applicationContext-dataAccess.xml</argument>
							</arguments>
						</configuration>
					</execution>
				</executions>
				<!-- <dependencies> <dependency> <groupId>com.feisystems.tools</groupId> 
					<artifactId>schema-export</artifactId> <version>0.0.1-SNAPSHOT</version> 
					</dependency> </dependencies> -->
			</plugin>
		</plugins>
	</build>

	<dependencies>
		<dependency>
			<groupId>com.feisystems.tools</groupId>
			<artifactId>schema-export</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

</profile>
 */
public class SpringEntityManagerFactoryBeanConfigSchemaExporter {

	public static void main(String[] args) {
		boolean drop = false;
		boolean create = false;
		String outFile = null;
		String delimiter = "";
		String activeSpringProfile = null;
		String dataAccessApplicationContextConfigClassPath = null;

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--")) {
				if (args[i].equals("--drop")) {
					drop = true;
				} else if (args[i].equals("--create")) {
					create = true;
				} else if (args[i].startsWith("--output=")) {
					outFile = args[i].substring(9);
				} else if (args[i].startsWith("--delimiter=")) {
					delimiter = args[i].substring(12);
				} else if (args[i].startsWith("--activeSpringProfile=")) {
					activeSpringProfile = args[i].substring(22);
				} else if (args[i].startsWith("--dataAccessApplicationContextConfigClassPath=")) {
					dataAccessApplicationContextConfigClassPath = args[i].substring(46);
				}
				
			}
		}

		Formatter formatter = FormatStyle.DDL.getFormatter();

		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		if (activeSpringProfile != null) {
			context.getEnvironment().setActiveProfiles(activeSpringProfile);
		}
		
		context.load(dataAccessApplicationContextConfigClassPath);

		context.refresh();

		AbstractEntityManagerFactoryBean bean = context
				.getBean(AbstractEntityManagerFactoryBean.class);

		Ejb3Configuration jpaConfiguration = new Ejb3Configuration().configure(
				bean.getPersistenceUnitInfo(), bean.getJpaPropertyMap());

		SchemaExporter.export(jpaConfiguration, drop, create, outFile, delimiter, formatter);

		context.close();
	}

	
}