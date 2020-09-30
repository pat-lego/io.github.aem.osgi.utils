package io.github.aem.forms.data.impl;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.aem.forms.data.DataParser;
import io.github.aem.forms.enums.DataType;
import io.github.aem.forms.exceptions.DataParserException;

@Component(
    service = DataParser.class,
    immediate = true,
    property = {
        "DataType=" + DataType.JSON
    }
)
public class JsonDataParser implements DataParser {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Configuration conf;

    @Override
    public List<String> getElement(String data, String path) throws DataParserException {
        if (data == null || data.isEmpty()) {
            throw new DataParserException("Cannot have an empty dataset when trying to retrieve value from data");
        }

        if (path == null || path.isEmpty()) {
            throw new DataParserException("No defined path to get data from");
        }

        JsonArray dataProps = JsonPath.using(this.conf).parse(data).read(path);
        List<String> items = new LinkedList<String>();
        for (int i = 0; i < dataProps.size(); i++) {
            if (dataProps.get(i).isJsonArray()) {
                throw new DataParserException("Cannot retrieve a JsonArray from the data, must be single value");
            } else if (dataProps.get(i).isJsonPrimitive()) {
                items.add(dataProps.get(i).getAsString());
            } else {
                throw new DataParserException("Cannot retrieve a JsonObject from the data, must be single value");
            }
            
        }

        return items;
    }

    @Activate
    public void activate() throws Exception {
        this.conf = Configuration.builder().jsonProvider(new GsonJsonProvider()).options(Option.ALWAYS_RETURN_LIST).build();
        logger.info(String.format("%s is now active", this.getClass().getName()));
    }

    @Deactivate
    public void deactivate() throws Exception {
        this.conf = Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST).addOptions(Option.SUPPRESS_EXCEPTIONS);
        logger.info(String.format("%s has been deactivated", this.getClass().getName()));
    }

    @Modified
    public void modified() throws Exception {
        this.conf = Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST);
        logger.info(String.format("%s has been modified", this.getClass().getName()));
    }
    
}