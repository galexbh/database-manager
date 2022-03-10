package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.DBUtils;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {

    @FXML
    private ComboBox<DBType> cbxDBType;

    @FXML
    private TextField txfBD;

    @FXML
    private PasswordField txfPassword;

    @FXML
    private TextField txfPort;

    @FXML
    private TextField txfServer;

    @FXML
    private TextField txfUser;

    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    void onTestConnection(ActionEvent event) {

        switch (cbxDBType.getValue()) {
            case MySQL -> DBUtils.setMySQLString(txfPort.getText(), txfServer.getText(), txfBD.getText(), txfUser.getText(), txfPassword.getText());

            case MariaDB -> DBUtils.setMariaDBSring(txfPort.getText(), txfServer.getText(), txfBD.getText(), txfUser.getText(), txfPassword.getText());

            case PostgreSQL -> DBUtils.setPostgreSQLString(txfPort.getText(), txfServer.getText(), txfBD.getText(), txfUser.getText(), txfPassword.getText());
        }

        DBAdapter dbAdapter = DBFactory.getDBAdapter(cbxDBType.getValue());

        if (dbAdapter.getConnection() != null) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection established");
            alert.show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxDBType.setItems(FXCollections.observableArrayList(DBType.values()));
        cbxDBType.getItems().setAll(DBType.values());
    }

}
