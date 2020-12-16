package com.holub.database;

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
        File file = new File("c:/dp2020/test.html");
        BufferedWriter w = new BufferedWriter(new FileWriter(file));
        w.write(out.toString());
        w.close();
    }
    
    @Test
    public void TestXMLExport() throws IOException, ParseFailure {
        Database theDatabase = new Database();
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

    @Test
    public void TestXMLImport() throws Exception {
        Database theDatabase = new Database();
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
        String original = out.toString();


        StringReader reader = new StringReader(original);
        Table t_load = new ConcreteTable(new XMLImporter( reader ));

        out = new StringWriter();
        xe = new XMLExporter(out);

        t_load.export(xe);

        String imported = out.toString();
        assertTrue("exported and imported&reexported string must be same",
            original.equals(imported));
    }

}
