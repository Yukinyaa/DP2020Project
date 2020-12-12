/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter {
	private final Writer out;

	public HTMLExporter(Writer out) {
		this.out = out;
	}

	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
		out.write("<head>\n<title>");
		out.write(tableName == null ? "anonymous table" : tableName);
		out.write("\t</title>\n</head>");
		out.write("\n\n");
		out.write("<body>\n");
		out.write(tableName == null ? "anonymous table" : tableName);
		out.write("<br/>\n<table border =\"1\">\n\n");
		storeRow(columnNames); // comma separated list of columns ids
	}

	public void storeRow(Iterator data) throws IOException {
		out.write("<tr>");
		while (data.hasNext()) {
			Object datum = data.next();
			out.write("\t<td>");
			out.write(datum.toString());
			out.write("</td>\n");
		}
		out.write("</tr>");
		out.write("\n");
	}

	public void startTable() throws IOException {
		out.write("<html>\n");
		/* nothing to do */
	}

	public void endTable() throws IOException {

		out.write("\n");
		out.write("</body>\n");
		out.write("</html>");

	}
}
