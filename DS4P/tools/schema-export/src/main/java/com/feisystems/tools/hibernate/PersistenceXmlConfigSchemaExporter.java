package com.feisystems.tools.hibernate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;

/*
 * Usage example in maven:
<profile>
	<!-- mvn clean install -Pdb-schema-create -->
	<id>db-schema-create</id>
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
							<mainClass>com.feisystems.tools.hibernate.PersistenceXmlConfigSchemaExporter</mainClass>
							<arguments>
								<argument>--create</argument>
								<argument>--delimiter=;</argument>
								<argument>--output=${project.basedir}/${project.build.finalName}-create-tables.sql</argument>
								<!-- The name of persistence-unit in persistence.xml -->
								<argument>--persistenceUnitName=persistenceUnit</argument>
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
public class PersistenceXmlConfigSchemaExporter {

	public static void main(String[] args) {
		boolean drop = false;
		boolean create = false;
		String outFile = null;
		String delimiter = "";
		String persistenceUnitName = null;

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
				} else if (args[i].startsWith("--persistenceUnitName=")){
					persistenceUnitName = args[i].substring(22);
				}
			}
		}

		Formatter formatter = FormatStyle.DDL.getFormatter();

		Ejb3Configuration jpaConfiguration = new Ejb3Configuration().configure(
				persistenceUnitName, null);
		
		SchemaExporter.export(jpaConfiguration, drop, create, outFile, delimiter, formatter);
	}
}