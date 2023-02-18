package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLReader {
    Parameters parametersClass = new Parameters();

    public Parameters read(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        Node root = doc.getDocumentElement();
        NodeList list = root.getChildNodes(); //считываем загрузку
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equals("log")) {
                    parametersClass.log = Boolean.parseBoolean(element.getElementsByTagName("enabled").item(0).getTextContent());
                    parametersClass.logFile = element.getElementsByTagName("fileName").item(0).getTextContent();
                } else {
                    if (element.getTagName().equals("save")) {
                        parametersClass.save = Boolean.parseBoolean(element.getElementsByTagName("enabled").item(0).getTextContent());
                        parametersClass.saveFile = element.getElementsByTagName("fileName").item(0).getTextContent();
                        parametersClass.saveType = element.getElementsByTagName("format").item(0).getTextContent();
                    } else {
                        parametersClass.load = Boolean.parseBoolean(element.getElementsByTagName("enabled").item(0).getTextContent());
                        parametersClass.loadFile = element.getElementsByTagName("fileName").item(0).getTextContent();
                        parametersClass.loadType = element.getElementsByTagName("format").item(0).getTextContent();
                    }
                }
            }

        }
        return (parametersClass);

    }
}
