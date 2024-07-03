package org.prototype.model;

//-------------------------------------- class Person --------------------------------------
public class Person {

    // -------------------------------------- Attributes --------------------------------------
    private String xmlName;
    private String excelName;
    private String xmlBirthDate;
    private String excelBirthDate;
    private String id;
    private boolean selected;

    // -------------------------------------- Constructor ---------------------------------------
    public Person(String xmlName, String excelName, String xmlBirthDate, String excelBirthDate, String id) {
        this.xmlName = xmlName;
        this.excelName = excelName;
        this.xmlBirthDate = xmlBirthDate;
        this.excelBirthDate = excelBirthDate;
        this.id = id;
        this.selected = false;
    }

    // -------------------------------------- Getters and Setters --------------------------------------
    public String getXmlName() {
        return xmlName;
    }

    // Not used in this prototype
    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getXmlBirthDate() {
        return xmlBirthDate;
    }

    // Not used in this prototype
    public void setXmlBirthDate(String xmlBirthDate) {
        this.xmlBirthDate = xmlBirthDate;
    }

    public String getExcelBirthDate() {
        return excelBirthDate;
    }

    public void setExcelBirthDate(String excelBirthDate) {
        this.excelBirthDate = excelBirthDate;
    }

    public String getId() {
        return id;
    }

    // Not used in this prototype
    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
