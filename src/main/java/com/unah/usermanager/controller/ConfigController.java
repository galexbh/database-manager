package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.DBUtils;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {

    @FXML
    private Button btnTestConn;

    @FXML
    private ComboBox<DBType> cbxSGBD;

    @FXML
    private TextField txfBD;

    @FXML
    private TextField txfInstance;

    @FXML
    private PasswordField txfPassword;

    @FXML
    private TextField txfPort;

    @FXML
    private TextField txfServer;

    @FXML
    private TextField txfUser;

    @FXML
    void onTestConnection(ActionEvent event) {

        switch (cbxSGBD.getValue()) {
            case MySQL -> DBUtils.setMySQLString(txfPort.getText(), txfServer.getText(), txfBD.getText(), txfUser.getText(), txfPassword.getText());

            case MSSQL -> DBUtils.setMSSQLString(txfPort.getText(), txfInstance.getText(), txfServer.getText(), txfBD.getText(), txfUser.getText(), txfPassword.getText());

            case PostgreSQL -> DBUtils.setPostgreSQLString(txfPort.getText(), txfServer.getText(), txfBD.getText(), txfUser.getText(), txfPassword.getText());
        }

        DBAdapter dbAdapter = DBFactory.getDBAdapter(cbxSGBD.getValue());

        dbAdapter.getConnection();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxSGBD.setItems(FXCollections.observableArrayList(DBType.values()));
        cbxSGBD.getItems().setAll(DBType.values());
    }

    public void onEnableInstance(ActionEvent actionEvent) {

        Object evt = actionEvent.getSource();
        if (evt.equals(cbxSGBD)) {
            txfInstance.setDisable(cbxSGBD.getValue() != DBType.MSSQL);
        }
    }
}