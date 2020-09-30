package io.github.aem.forms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.aem.forms.data.impl.XmlDataParser;
import io.github.aem.forms.exceptions.DataParserException;

public class XmlDataParserTest {

    public final String xml = "<data> <person> <firstName>Pat</firstName> <lastName>Legault</lastName> <age>12</age> <address> <street>1234 Some road</street> <countryCode>CA</countryCode> </address> </person> </data>";

    @Test
    public void testGetXmlString() {
        XmlDataParser xmlParser = new XmlDataParser();
        List<String> data = xmlParser.getElement(xml, "/data/person/address/street");

        assertTrue(!data.isEmpty());
        assertEquals(1, data.size());
        assertEquals("1234 Some road", data.get(0));
    }

    @Test
    public void testGetXml_empty_data() {
        XmlDataParser xmlParser = new XmlDataParser();
        Assertions.assertThrows(DataParserException.class, () -> {
            xmlParser.getElement(null, "/data/person/address/street");
        });
    }

    @Test
    public void testGetXml_empty_path() {
        XmlDataParser xmlParser = new XmlDataParser();
        Assertions.assertThrows(DataParserException.class, () -> {
            xmlParser.getElement(xml, StringUtils.EMPTY);
        });
    }
}