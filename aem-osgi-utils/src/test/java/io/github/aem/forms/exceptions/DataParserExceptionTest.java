package io.github.aem.forms.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataParserExceptionTest {

    @Test
    public void testSetupMsg() {
        DataParserException parser = new DataParserException("Failed");
        Assertions.assertEquals("Failed", parser.getMessage());
    }

    @Test
    public void testSetupThrowable() {
        Throwable t = new Throwable();
        DataParserException parser = new DataParserException("Failed", t);
        Assertions.assertEquals("Failed", parser.getMessage());
    }
    
}
