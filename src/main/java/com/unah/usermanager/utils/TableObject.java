package com.unah.usermanager.utils;

public class TableObject {
    private String nameField;
    private boolean isNull;

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
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

    private int tamField;
    private String typeField;

}
