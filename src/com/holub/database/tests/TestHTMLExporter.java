package com.holub.database.tests;

import java.io.*;

import javax.swing.text.html.HTML;

import com.holub.database.*;
import com.holub.text.ParseFailure;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestHTMLExporter {
    @Test
    public void TestHTMLExport() throws IOException, ParseFailure {
        Database theDatabase = new Database();
        theDatabase.dropTable("testHTML");
        theDatabase.useDatabase(new File("c:/dp2020/"));

        theDatabase.execute("drop table HTMLExportTest");
        theDatabase.execute("create table HTMLExportTest (one, two)");
        theDatabase.execute("insert into HTMLExportTest values('a', 1)");
        theDatabase.execute("insert into HTMLExportTest values('b', 2)");
        theDatabase.execute("insert into HTMLExportTest values('c', 3)");
        theDatabase.execute("insert into HTMLExportTest values('d', 4)");

        Table t = theDatabase.execute("select * from HTMLExportTest");
        Writer out = new StringWriter();
        HTMLExporter he = new HTMLExporter(out);

        t.export(he);
        System.out.println(out.toString());

    }
}
