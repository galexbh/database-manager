package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.TableObject;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.unah.usermanager.controller.TableViewController.*;

public class InsertFieldController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button insertButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        for (int i = 1; i < columnsNames.size(); i++) {
            int tempPositionY = 30;
            if (!contentPane.getChildren().isEmpty()){
                Pane tempPaneField = (Pane) contentPane.getChildren().get(contentPane.getChildren().size() - 1 );
                tempPositionY += tempPaneField.getLayoutY() + tempPaneField.getPrefHeight();
            }

            Pane paneField = new Pane();
            paneField.setPrefWidth(contentPane.getPrefWidth() - 100);
            paneField.setLayoutX((contentPane.getPrefWidth() - paneField.getPrefWidth()) / 2);
            paneField.setPrefHeight(30);
            paneField.setLayoutY(tempPositionY);


            Label campName = new Label();

            campName.setPrefHeight(30);
            campName.setText(columnsNames.get(i) + ":");
            campName.setPrefWidth(campName.getText().toCharArray().length*8);
            campName.setId("campName");
            paneField.getChildren().add(campName);

            double tempLayoutX = 0;
            double tempPrefWidth = 0;
            if (columnsType.get(i) == "DATE"){
                DatePicker dateField = new DatePicker();
                dateField.setPrefWidth(200);
                dateField.setPrefHeight(30);
                dateField.setId("dateField");
                dateField.setLayoutX(campName.getPrefWidth() + 20);
                tempLayoutX = dateField.getLayoutX();
                tempPrefWidth = dateField.getPrefWidth();

                paneField.getChildren().add(dateField);

            } else {
                TextField valueField = new TextField();
                valueField.setPrefWidth(200);
                valueField.setPrefHeight(30);
                valueField.setPromptText("Valor a ingresar");
                valueField.setId("textField");
                valueField.setLayoutX(campName.getPrefWidth() + 20);
                tempLayoutX = valueField.getLayoutX();
                tempPrefWidth = valueField.getPrefWidth();

                paneField.getChildren().add(valueField);

            }


            Label dataType = new Label();
            dataType.setPrefHeight(30);
            dataType.setText("Tipo de dato: " + columnsType.get(i));
            dataType.setPrefWidth(dataType.getText().toCharArray().length*8);
            dataType.setId("dataType");
            dataType.setLayoutX(tempLayoutX +  tempPrefWidth + 20);

            paneField.getChildren().add(dataType);
            contentPane.getChildren().add(paneField);
        }

    }

    @FXML
    private void date(DatePicker date) {
        if (date.getValue() == null ) {
        return;
        }
        System.out.println(java.sql.Date.valueOf(date.getValue().toString()));
    }
    @FXML
    private void insertField() {
        DBAdapter dbAdapter = null;
        switch (dataBase) {
            case "MySQL":
                dbAdapter = DBFactory.getDBAdapter(DBType.MySQL);
                break;

            case "PostgreSQL":
                dbAdapter = DBFactory.getDBAdapter(DBType.PostgreSQL);
                break;
            case "MariaDB":
                dbAdapter = DBFactory.getDBAdapter(DBType.MariaDB);
                break;
            default:
                break;
        }
        Connection connection = dbAdapter.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO " + tableValue + " ";
        String fieldNames = "(";
        String tempComa = ",";

        for (int i = 1; i < columnsNames.size(); i++) {
            if (i == (columnsNames.size() - 1)) {
                tempComa = "";
            }

            fieldNames = fieldNames + columnsNames.get(i) + tempComa;

        }


        fieldNames = fieldNames + ")";
        query = query + fieldNames + " VALUES (" ;


            tempComa = ",";
        String quotation = "";
        for (int i = 0; i < contentPane.getChildren().size(); i++) {
                Pane fieldValues = (Pane) contentPane.getChildren().get(i);
                if (i == (contentPane.getChildren().size() -1 )){
                    tempComa = ")";
                }
                for (Node fieldValue : fieldValues.getChildren()) {
                    switch (fieldValue.getId()) {
                        case "textField":
                            switch(columnsType.get(i + 1)){
                                case "CHAR","VARCHAR","TEXT","MEDIUMTEXT":
                                    quotation = "\"";

                                    break;
                                default:
                                    break;
                            }
                            TextField tempTextField = (TextField) fieldValue;
                                query = query + quotation + tempTextField.getText() + quotation + tempComa;
                            break;
                        case "dateField":
                            DatePicker tempDateField = (DatePicker) fieldValue;
                                query = query + "\"" + java.sql.Date.valueOf(tempDateField.getValue().toString()) + "\"" + tempComa;
                            break;
                        default:
                            break;

                    }
                }

            }


        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        columnsType = FXCollections.observableArrayList();
        columnsNames = FXCollections.observableArrayList();
        tableValue ="";

        Stage stage = (Stage) insertButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    private void closeWindow(){
        columnsType = FXCollections.observableArrayList();
        columnsNames = FXCollections.observableArrayList();
        tableValue ="";
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
