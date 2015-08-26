package com.nekomimi.util;

import android.provider.DocumentsContract;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hongchi on 2015-8-21.
 */
public class XmlDomUtil {

    public void parse(InputStream is) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);


    }
}
