package com.unah.usermanager.utils;

public class TableObject {
    private String nameField;
    private String isNull;
    private int tamField;
    private String typeField;

    public String generateField(){
        return nameField + " " + typeField +"(" + String.valueOf(tamField) + ")" + " " + isNull;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String isNull() {
        return isNull;
    }

    public void setNull(String aNull) {
        isNull = aNull;
    }

    public int getTamField() {
        return tamField;
    }

    public void setTamField(int tamField) {
        this.tamField = tamField;
    }

    public String getTypeField() {
        return typeField;
    }

    public void setTypeField(String typeField) {
        this.typeField = typeField;
    }



}
