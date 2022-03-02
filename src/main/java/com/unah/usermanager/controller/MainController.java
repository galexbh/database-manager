package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        DBAdapter dbAdapter = DBFactory.getDBAdapter(DBFactory.DBType.PostgreSQL);
        Connection connection = dbAdapter.getConnection();
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}