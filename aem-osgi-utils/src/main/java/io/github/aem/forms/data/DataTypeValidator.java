package io.github.aem.forms.data;


public interface DataTypeValidator {

    /**
     * Used to determine the data type of a complex structure. For instance if you
     * have a String that is formatted for JSON then the getDataType function will
     * return the `JSON` enum from DataType. If you have a string that is formatted
     * for XML then it will return an XML structure.
     * 
     * If its not a supported format from the DataType enum then it will return null
     * 
     * @param data A string that represents a complex data structure
     * @return String class from the DataType
     */
    public String getDataType(String data);

    /**
     * Used to validate if the data structure is JSON
     * 
     * @param data Data
     * @return Boolean
     */
    public Boolean isJson(String data);

    /**
     * Used to validate if the data structure is XML
     * 
     * @param data Data
     * @return Boolean
     */
    public Boolean isXML(String data);
}