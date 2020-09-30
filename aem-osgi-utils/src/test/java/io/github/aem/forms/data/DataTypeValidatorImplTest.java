package io.github.aem.forms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.aem.forms.data.impl.DataTypeValidatorImpl;
import io.github.aem.forms.enums.DataType;

public class DataTypeValidatorImplTest {

    public final String xml = "<data> <person> <firstName>Pat</firstName> <lastName>Legault</lastName> <address> <street>1234 Some road</street> <countryCode>CA</countryCode> </address> </person> </data>";
    public final String xml_with_declaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><data> <person> <firstName>Pat</firstName> <lastName>Legault</lastName> <address> <street>1234 Some road</street> <countryCode>CA</countryCode> </address> </person> </data>";
    public final String json = "{ \"person\": { \"firstName\": \"Patrique\", \"lastName\": \"Legault\", \"address\": { \"street\": \"1234 Some road\" } } }";
    @Test
    public void testValidXML() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        String data = dataValidator.getDataType(xml);

        assertEquals(DataType.XML, data);
    }

    @Test
    public void testValidXML_with_declaration() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        String data = dataValidator.getDataType(xml_with_declaration);

        assertEquals(DataType.XML, data);
    }

    @Test
    public void testIsValidXML_with_declaration() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        Boolean data = dataValidator.isXML(xml_with_declaration);

        assertEquals(Boolean.TRUE, data);
    }

    @Test
    public void testIsValidXML() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        Boolean data = dataValidator.isXML(xml);

        assertEquals(Boolean.TRUE, data);
    }

    @Test
    public void testIsValidXML_False() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        Boolean data = dataValidator.isXML(json);

        assertEquals(Boolean.FALSE, data);
    }

    @Test
    public void testValidJson() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        String data = dataValidator.getDataType(json);

        assertEquals(DataType.JSON, data);
    }

    @Test
    public void testIsValidJson() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        Boolean data = dataValidator.isJson(json);

        assertEquals(Boolean.TRUE, data);
    }

    @Test
    public void testIsValidJson_False() {
        DataTypeValidatorImpl dataValidator = new DataTypeValidatorImpl();
        Boolean data = dataValidator.isJson(xml);

        assertEquals(Boolean.FALSE, data);
    }
    
}