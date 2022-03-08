package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddUserController {
    @FXML
    private TextField txfPassword;

    @FXML
    private TextField txfUser;

    static Statement statement = null;
    static ResultSet resultSet = null;
    boolean isResultSet = false;

    @FXML
    void onAddUser(ActionEvent event) throws SQLException {
        DBAdapter dbAdapter = DBFactory.getDBAdapter(DBType.MySQL);
        Connection connection = dbAdapter.getConnection();

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String query = createUser(txfUser.getText(), txfPassword.getText());
        isResultSet = statement.execute(query);

        if (isResultSet){
            System.out.println("Operación realizada");
        }

    }

    public String createUser(String user, String password) {
        return "CREATE USER" + user + " IDENTIFIED BY " + password + ";";
    }
}
