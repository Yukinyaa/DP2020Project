package com.holub.database;

import java.io.*;

import com.holub.database.Table.Exporter;
import com.holub.text.ParseFailure;

import org.junit.Test;

public class TestDatabase {
	@Test
	public void Default() throws IOException, ParseFailure {
		Database theDatabase = new Database();

		// Read a sequence of SQL statements in from the file
		// Database.test.sql and execute them.

		BufferedReader sql = new BufferedReader(new FileReader("c:\\dp2020\\Database.test.sql"));
		String test;
		while ((test = sql.readLine()) != null) {
			test = test.trim();
			if (test.length() == 0)
				continue;

			while (test.endsWith("\\")) {
				test = test.substring(0, test.length() - 1);
				test += sql.readLine().trim();
			}

			System.out.println("Parsing: " + test);
			Table result = theDatabase.execute(test);

			if (result != null) // it was a SELECT of some sort
				System.out.println(result.toString());
		}

		try {
			theDatabase.execute("insert garbage SQL");
			System.out.println("Database FAILED");
			System.exit(1);
		} catch (ParseFailure e) {
			System.out.println("Correctly found garbage SQL:\n" + e + "\n" + e.getErrorReport());
		}

		theDatabase.dump();

		System.out.println("Database PASSED");
	}
	@Test
	public void DoJoinTest() throws IOException, ParseFailure
	{
		Database theDatabase = new Database("c:\\dp2020\\");
		Table result;
		result = theDatabase.execute("select addrId from address, name where address.addrId = name.addrId");
		
        Writer out = new StringWriter();
		Exporter exporter = new CSVExporter(out);
		result.export(exporter);

		System.out.println(out.toString());
		
		result = theDatabase.execute("select * from address");
		
        out = new StringWriter();
		exporter = new CSVExporter(out);
		result.export(exporter);

		System.out.println(out.toString());
		

		result = theDatabase.execute("select * from address, name where address.addrId = name.addrId");

		out = new StringWriter();
		exporter = new CSVExporter(out);
		result.export(exporter);

		System.out.println(out.toString());
	}
}