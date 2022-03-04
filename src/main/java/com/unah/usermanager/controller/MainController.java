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
    private VBox vbConfig;

    @FXML
    private Button btnConfig;

    @FXML
    void onChangeView(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(btnConfig)) {
            vbConfig.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            FXMLLoader loader = loadForm("/com/unah/usermanager/config-view.fxml");
            vbConfig = loader.load();
            spContainer.getChildren().addAll(vbConfig);
            vbConfig.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FXMLLoader loadForm(String url) {
        return new FXMLLoader(getClass().getResource(url));
    }
}

