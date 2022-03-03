package com.unah.usermanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!"}

}

/* *
DBAdapter dbAdapter = DBFactory.getDBAdapter(DBFactory.DBType.MSSQL);
Connection connection = dbAdapter.getConnection();
* */