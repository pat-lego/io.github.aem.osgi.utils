package io.github.aem.forms.data.impl;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import io.github.aem.forms.data.DataParser;
import io.github.aem.forms.enums.DataType;
import io.github.aem.forms.exceptions.DataParserException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Component(service = DataParser.class, immediate = true, property = { "DataType=" + DataType.XML })
public class XmlDataParser implements DataParser {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<String> getElement(String data, String path) throws DataParserException {
        if (data == null || data.isEmpty()) {
            throw new DataParserException("Cannot have an empty dataset when trying to retrieve value from data");
        }

        if (path == null || path.isEmpty()) {
            throw new DataParserException("No defined path to get data from");
        }

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(data));
            Document xmlDocument = builder.parse(is);
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xPath.compile(path).evaluate(xmlDocument, XPathConstants.NODESET);
            
            List<String> result = new LinkedList<String>();
            for (int i = 0; i < list.getLength(); i++) {
                result.add(list.item(i).getTextContent());
            }

            return result;
        } catch (Exception e) {
            throw new DataParserException("Unable to retrieve XPath attribute from data", e);
        }

    }

    @Activate
    protected void activate() {
        logger.info(String.format("%s is now active", this.getClass().getName()));
    }

    @Deactivate
    protected void deactivate() {
        logger.info(String.format("%s has been deactivated", this.getClass().getName()));
    }

}