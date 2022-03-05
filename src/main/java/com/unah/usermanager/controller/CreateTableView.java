package com.unah.usermanager.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CreateTableView {

    @FXML
    private Pane tableParent;


    @FXML
    private void addField(){

        Pane paneField = new Pane();
        textField.setPrefWidth(200);
        textField.setPrefHeight(30);
        int tempPositionY = 30;
        if (!tableParent.getChildren().isEmpty()){
            TextField tempTextField = (TextField) tableParent.getChildren().get(tableParent.getChildren().size() - 1 );
            tempPositionY += tempTextField.getLayoutY() + tempTextField.getPrefHeight();
        }

        textField.setLayoutX((tableParent.getPrefWidth()/2) - (textField.getPrefWidth()/2));
        textField.setLayoutY(tempPositionY);
        tableParent.getChildren().add(textField);
    }

    @FXML
    private void removeField(){
        if (tableParent.getChildren().isEmpty()){return; }
        tableParent.getChildren().removeAll(tableParent.getChildren().get(tableParent.getChildren().size() - 1));

}
}
