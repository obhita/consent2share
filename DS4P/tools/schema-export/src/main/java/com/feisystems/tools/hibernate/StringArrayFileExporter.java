package com.feisystems.tools.hibernate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.hibernate.engine.jdbc.internal.Formatter;

public class StringArrayFileExporter {
	
	public static void export(String outFile, String delimiter,
			Formatter formatter, String[] stringArray) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outFile);
			for (String string : stringArray) {
				writer.print(formatter.format(string) + "\n" + delimiter + "\n");
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
