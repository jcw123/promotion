package com.im.sky.xml;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

/**
 * @author jiangchangwei
 * @date 2020-12-28 上午 11:34
 **/
public class Dom4JTest {

    public static void main(String[] args) throws Exception {
        createXml();
    }

    private static void createXml() throws Exception {
        Document document = DocumentHelper.createDocument();
        Element peoples = document.addElement("peoples");
        Element p1 = peoples.addElement("people");
        p1.setText("m1");
        Element p2 = peoples.addElement("people");
        p2.setText("m2");
        peoples.setName("t1");
        peoples.setQName(new QName("t2"));
        XMLWriter xmlWriter = new XMLWriter(System.out);
        xmlWriter.write(document);
        xmlWriter.close();
    }


    private static void parseXml() {
        SAXReader saxReader = new SAXReader();
    }
}
