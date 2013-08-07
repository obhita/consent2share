package com.feisystems.tools.hibernate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.engine.jdbc.internal.Formatter;

public class SchemaExporter {

	public static void export(Ejb3Configuration jpaConfiguration, boolean drop,
			boolean create, String outFile, String delimiter,
			Formatter formatter) {

		SchemaGenerator schemaGenerator = new SchemaGenerator(jpaConfiguration);

		if (create) {
			String[] createSQL = schemaGenerator.generateSchemaCreationScript();
			StringArrayFileExporter.export(outFile, delimiter, formatter,
					createSQL);
		}

		if (drop) {
			String[] dropSQL = schemaGenerator.generateDropSchemaScript();
			StringArrayFileExporter.export(outFile, delimiter, formatter,
					dropSQL);
		}
	}
}
