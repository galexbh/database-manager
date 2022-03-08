package com.unah.usermanager.model;

import javafx.beans.value.ObservableValue;

public class userModel {
    private String User, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv;

    public userModel(String user, String select_priv, String insert_priv, String update_priv, String delete_priv, String create_priv, String drop_priv) {
        User = user;
        Select_priv = select_priv;
        Insert_priv = insert_priv;
        Update_priv = update_priv;
        Delete_priv = delete_priv;
        Create_priv = create_priv;
        Drop_priv = drop_priv;
    }

    public String getUser() {
        return User;
    }

    public String getSelect_priv() {
        return Select_priv;
    }

    public String getInsert_priv() {
        return Insert_priv;
    }

    public String getUpdate_priv() {
        return Update_priv;
    }

    public String getDelete_priv() {
        return Delete_priv;
    }

    public String getCreate_priv() {
        return Create_priv;
    }

    public String getDrop_priv() {
        return Drop_priv;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setSelect_priv(String select_priv) {
        Select_priv = select_priv;
    }

    public void setInsert_priv(String insert_priv) {
        Insert_priv = insert_priv;
    }

    public void setUpdate_priv(String update_priv) {
        Update_priv = update_priv;
    }

    public void setDelete_priv(String delete_priv) {
        Delete_priv = delete_priv;
    }

    public void setCreate_priv(String create_priv) {
        Create_priv = create_priv;
    }

    public void setDrop_priv(String drop_priv) {
        Drop_priv = drop_priv;
    }
}
