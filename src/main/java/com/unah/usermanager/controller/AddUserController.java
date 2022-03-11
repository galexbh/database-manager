package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.unah.usermanager.controller.ManagementController.dbtype;

public class AddUserController {
    @FXML
    private TextField txfPassword;

    @FXML
    private TextField txfUser;

    static Statement statement = null;
    boolean isResultSet = false;

    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    void onAddUser(ActionEvent event) throws SQLException {
        DBAdapter dbAdapter = DBFactory.getDBAdapter(dbtype);
        Connection connection = dbAdapter.getConnection();

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String query = createUser(txfUser.getText(), txfPassword.getText());
        isResultSet = statement.execute(query);

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Operation successful");
        alert.show();


    }

    public String createUser(String user, String password) {
        switch (dbtype) {
            case MySQL, MariaDB: {
                return "CREATE USER " + user + " IDENTIFIED BY " + password + ";";
            }
            case PostgreSQL: {
                return "CREATE USER " + user + " WITH " + "PASSWORD " + "'" + password + "';";
            }
            default:
                return null;
        }
    }
}

