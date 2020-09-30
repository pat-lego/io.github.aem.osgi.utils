package io.github.aem.forms.exceptions;

public class DataParserException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3201535378232452786L;

    public DataParserException(String msg) {
        super(msg);
    }

    public DataParserException(String msg, Throwable t) {
        super(msg, t);
    }

    
}