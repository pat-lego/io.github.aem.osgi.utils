package io.github.aem.forms.data.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import io.github.aem.forms.data.DataTypeValidator;
import io.github.aem.forms.enums.DataType;

@Component(service = DataTypeValidator.class, immediate = true)
public class DataTypeValidatorImpl implements DataTypeValidator {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getDataType(String data) {
        // Empty strings aren't anything
        if (data == null || data.isEmpty()) {
            return null;
        }

        // Test against JSON
        try {
            if (this.validateJson(data)) {
                return DataType.JSON;
            }

        } catch (JsonParseException | MalformedJsonException e) {
            logger.warn("Failed to validate data against JSON data type");
        }

        // Test against XML
        try {
            if (this.validateXML(data)) {
                return DataType.XML;
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.warn("Failed to validate data against XML data type");
        }

        // Nothing worked
        return null;
    }

    public Boolean validateJson(String data) throws JsonParseException, MalformedJsonException, JsonSyntaxException {
        JsonParser.parseString(data);
        return Boolean.TRUE;
    }

    public Boolean validateXML(String data) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(new ByteArrayInputStream(data.getBytes()), new DefaultHandler());
        return Boolean.TRUE;
    }

    @Override
    public Boolean isJson(String data) {
        if (data == null || data.isEmpty()) {
            return Boolean.FALSE;
        }
        try {
            this.validateJson(data);
            return Boolean.TRUE;
        } catch (JsonParseException | MalformedJsonException e) {
            return Boolean.FALSE;
        } 
    }

    @Override
    public Boolean isXML(String data) {
        if (data == null || data.isEmpty()) {
            return Boolean.FALSE;
        }
        try {
            this.validateXML(data);
            return Boolean.TRUE;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return Boolean.FALSE;
        } 
    }
    
}