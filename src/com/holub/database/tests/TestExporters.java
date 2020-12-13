package com.holub.database.tests;

import java.io.*;

import javax.swing.text.html.HTML;

import com.holub.database.*;
import com.holub.text.ParseFailure;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestExporters {
    @Test
    public void TestHTMLExport() throws IOException, ParseFailure {
        Database theDatabase = new Database();
        theDatabase.dropTable("testHTML");
        theDatabase.useDatabase(new File("c:/dp2020/"));

        theDatabase.execute("drop table ExportTest");
        theDatabase.execute("create table ExportTest (one, two)");
        theDatabase.execute("insert into ExportTest values('a', 1)");
        theDatabase.execute("insert into ExportTest values('b', 2)");
        theDatabase.execute("insert into ExportTest values('c', 3)");
        theDatabase.execute("insert into ExportTest values('d', 4)");

        Table t = theDatabase.execute("select * from ExportTest");
        Writer out = new StringWriter();
        HTMLExporter he = new HTMLExporter(out);

        t.export(he);
        System.out.println(out.toString());

    }
    
    @Test
    public void TestXMLExport() throws IOException, ParseFailure {
        Database theDatabase = new Database();
        theDatabase.dropTable("testHTML");
        theDatabase.useDatabase(new File("c:/dp2020/"));

        theDatabase.execute("drop table ExportTest");
        theDatabase.execute("create table ExportTest (one, two)");
        theDatabase.execute("insert into ExportTest values('a', 1)");
        theDatabase.execute("insert into ExportTest values('b', 2)");
        theDatabase.execute("insert into ExportTest values('c', 3)");
        theDatabase.execute("insert into ExportTest values('d', 4)");

        Table t = theDatabase.execute("select * from ExportTest");
        Writer out = new StringWriter();
        XMLExporter xe = new XMLExporter(out);

        t.export(xe);
        System.out.println(out.toString());

    }
}
