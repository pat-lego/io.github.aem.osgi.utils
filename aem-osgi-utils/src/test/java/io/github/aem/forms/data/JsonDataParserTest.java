package io.github.aem.forms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.aem.forms.data.impl.JsonDataParser;
import io.github.aem.forms.exceptions.DataParserException;

public class JsonDataParserTest {

    public final String json = "{ \"person\": { \"firstName\": \"Patrique\", \"lastName\": \"Legault\", \"age\": 12, \"address\": { \"street\": \"1234 Some road\" } } }";
    public final String formDataJson = "{ \"afData\": { \"afUnboundData\": { \"data\": {} }, \"afBoundData\": { \"data\": { \"person_1598972290495\": { \"firstName\": \"asdfgasg\", \"lastName\": \"fag\" } } }, \"afSubmissionInfo\": { \"computedMetaInfo\": {}, \"signers\": {}, \"afPath\": \"/content/dam/formsanddocuments/email-submission/test\", \"afSubmissionTime\": \"20200901081800\" } } }";

    @Test
    public void getElementString_withFormData() throws Exception {
        JsonDataParser jsonParser = new JsonDataParser();
        jsonParser.activate();
        List<String> afPath = jsonParser.getElement(formDataJson, "$.afData.afSubmissionInfo.afPath");
        assertTrue(!afPath.isEmpty());
        assertEquals(1, afPath.size());
        assertEquals("/content/dam/formsanddocuments/email-submission/test", afPath.get(0));
    }

    @Test
    public void getElementString() throws Exception {
        JsonDataParser jsonParser = new JsonDataParser();
        jsonParser.activate();
        List<String> street = jsonParser.getElement(json, "$.person.address.street");
        assertTrue(!street.isEmpty());
        assertEquals(1, street.size());
        assertEquals("1234 Some road", street.get(0));
    }

    @Test
    public void getElemen_empty_data() throws Exception {
        JsonDataParser jsonParser = new JsonDataParser();
        jsonParser.activate();
        Assertions.assertThrows(DataParserException.class, () -> {
            jsonParser.getElement(null, "$.person.address.street");
        });
       
    }

    @Test
    public void getElemen_empty_path() throws Exception {
        JsonDataParser jsonParser = new JsonDataParser();
        jsonParser.activate();
        Assertions.assertThrows(DataParserException.class, () -> {
            jsonParser.getElement(json, StringUtils.EMPTY);
        });
       
    }

    @Test
    public void testOSGi() throws Exception {
        JsonDataParser jsonParser = new JsonDataParser();
        jsonParser.activate();
        jsonParser.deactivate();
        jsonParser.modified();
    }
}