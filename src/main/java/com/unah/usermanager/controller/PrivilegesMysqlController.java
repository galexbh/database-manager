package com.unah.usermanager.controller;

import com.unah.usermanager.model.userModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class PrivilegesMysqlController {

    @FXML
    private ComboBox<userModel> cbxUser;

    @FXML
    private CheckBox chbxCreate;

    @FXML
    private CheckBox chbxDelete;

    @FXML
    private CheckBox chbxDrop;

    @FXML
    private CheckBox chbxInsert;

    @FXML
    private CheckBox chbxSelect;

    @FXML
    private CheckBox chbxUpdate;

    @FXML
    private ListView<String> lsvPriv;

    @FXML
    void onGrantPrivileges(ActionEvent event) {

    }

    @FXML
    void onRevokePrivileges(ActionEvent event) {

    }
}
