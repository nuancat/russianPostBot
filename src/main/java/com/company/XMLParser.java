package com.company;

import org.jdom2.*;

import org.jdom2.filter.ElementFilter;
import org.jdom2.input.*;
import org.jdom2.util.IteratorIterable;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by sha on 04.09.2016.
 */
public class XMLParser {

    String parse(InputStream x) throws JDOMException, IOException {

        SAXBuilder parser = new SAXBuilder();
        Document xmlDoc;
        xmlDoc = parser.build(x);
        IteratorIterable<Element> elements = xmlDoc.getDescendants(new ElementFilter("historyRecord"));
        StringBuilder sb = new StringBuilder("");
        int c = 1;
        while (elements.hasNext()) {
            Element history = (Element) elements.next();
            String datetime = history.getChild("OperationParameters",history.getNamespace()).getChildText("OperDate",history.getNamespace());
            String name = history.getChild("OperationParameters",history.getNamespace()).getChild("OperType",history.getNamespace()).getChildText("Name",history.getNamespace());
            String country = history.getChild("AddressParameters",history.getNamespace()).getChild("OperationAddress",history.getNamespace()).getChildText("Description",history.getNamespace());
            sb.append(c+". "+name + " "+ country+" "+datetime.substring(0,19).replaceAll("T"," ")+"\n\n");
            c++;
        }
        return sb.toString();
    }
}