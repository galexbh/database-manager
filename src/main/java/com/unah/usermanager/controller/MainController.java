package com.unah.usermanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    @FXML
    private StackPane spContainer;

    @FXML
    private VBox vbConfig, vbAbout;

    @FXML
    private Button btnConfig,btnAbout;

    @FXML
    void onChangeView(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(btnConfig)) {
            vbConfig.setVisible(true);
            vbAbout.setVisible(false);
        }
        if (evt.equals(btnAbout)) {
            vbAbout.setVisible(true);
            vbConfig.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            FXMLLoader ldConfig = loadForm("/com/unah/usermanager/config-view.fxml");
            FXMLLoader ldAbout = loadForm("/com/unah/usermanager/about-view.fxml");
            vbConfig = ldConfig.load();
            vbAbout = ldAbout.load();
            spContainer.getChildren().addAll(vbConfig,vbAbout);
            vbConfig.setVisible(false);
            vbAbout.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FXMLLoader loadForm(String url) {
        return new FXMLLoader(getClass().getResource(url));
    }
}

