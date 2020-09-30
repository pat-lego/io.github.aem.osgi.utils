package io.github.aem.forms.data;

import java.util.List;

import io.github.aem.forms.exceptions.DataParserException;

public interface DataParser {
    
    public List<String> getElement(String data, String path) throws DataParserException;
}