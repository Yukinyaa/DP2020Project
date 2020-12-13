package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLImporter implements Table.Importer {
    private BufferedReader in; // null once end-of-file reached
    private String[] columnNames;
    private String tableName;
    Document table;
    int columnLength;

    public XMLImporter(Reader in) throws Exception {
        this.in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        InputSource is = new InputSource();
        is.setCharacterStream(this.in);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        dbFactory.setIgnoringElementContentWhitespace(true);
        dbFactory.setNamespaceAware(true);

        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            table = dBuilder.parse(is);
        } catch (SAXException | IOException |ParserConfigurationException e) {
            e.printStackTrace();
            throw e;
        }                        
	}
	public void startTable()			throws IOException
	{	
        
        tableName   = table.getDocumentElement().getAttribute("name");
        var colNames = table.getDocumentElement().getElementsByTagName("columnNames").item(0).getChildNodes();
        columnLength = 0;
        columnNames = new String[colNames.getLength()];

        for(int i = 0;i<colNames.getLength();i++)
        {
            if(!colNames.item(i).getNodeName().equals("data"))
                continue;
            columnNames[columnLength] = colNames.item(i).getTextContent();
            columnLength++;
        }
        columnNames = Arrays.copyOfRange(columnNames, 0, columnLength);
        colNo = 1;
	}
	public String loadTableName()		throws IOException
	{	return tableName;
	}
	public int loadWidth()			    throws IOException
	{	return columnLength;
	}
	public Iterator loadColumnNames()	throws IOException
	{	return new ArrayIterator(columnNames);  //{=CSVImporter.ArrayIteratorCall}
	}

    int colNo;
	public Iterator loadRow() throws IOException
	{	
        Iterator row = null;
		while( table.getDocumentElement().getChildNodes().getLength() > colNo )
		{
            Node rowObj = table.getDocumentElement().getChildNodes().item(colNo++);
            if(!rowObj.getNodeName().equals("row"))
                continue;
            var rowChilds = rowObj.getChildNodes();
            var rowArr = new String[columnLength];
            int j = 0;
            for(int i = 0;i<rowChilds.getLength();i++)
            {
                if(rowChilds.item(i).getNodeName().equals("data"))
                    rowArr[j++] = rowChilds.item(i).getTextContent();
            }
            row = new ArrayIterator(rowArr);
            return row;
        }
        return null;
	}

	public void endTable() throws IOException {}
}
