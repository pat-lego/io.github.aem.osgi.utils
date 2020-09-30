package io.github.aem.forms.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataTypeTest {

    @Test
    public void testSimple() {
        Assertions.assertEquals("JSON", DataType.JSON);
        Assertions.assertEquals("XML", DataType.XML);
    }
    
}
