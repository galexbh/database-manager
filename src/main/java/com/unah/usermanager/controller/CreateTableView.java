package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.DBUtils;
import com.unah.usermanager.utils.TableObject;
import com.unah.usermanager.utils.interfaces.ComboBoxsDataSource;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CreateTableView implements Initializable, ComboBoxsDataSource {

    @FXML
    private Pane tableParent;
    @FXML
    private TextField tableName;
    @FXML
    private ComboBox listSGBD;

    private ObservableList<String> ComboBoxdataTypeList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableName.setOnMouseClicked((event) -> removeBorder(tableName));
        listSGBD.setOnMouseClicked((event) -> removeBorder(listSGBD));
        listSGBD.setItems(FXCollections.observableArrayList("MySQL","PostgreSQL"));
    }

    @FXML
    private void selectSGBD(){

        switch((String) listSGBD.getSelectionModel().getSelectedItem()){
            case "MySQL":
                ComboBoxdataTypeList = mysqlDataType;
                break;
            case "PostgreSQL":
                ComboBoxdataTypeList = mysqlDataType;
                break;
            default:
                break;
        }
        if (!tableParent.getChildren().isEmpty()){
            for (Node fieldPane: tableParent.getChildren()){
                Pane fieldValues = (Pane) fieldPane;
                for (Node fieldValue:fieldValues.getChildren()){
                    if (fieldValue.getId() == "dataTypeList"){
                        ComboBox tempDataTypeList = (ComboBox) fieldValue;
                        tempDataTypeList.setItems(ComboBoxdataTypeList);
                        tempDataTypeList.setPromptText("Tipo de dato");


                    }
                }
            }
        }
    }
    @FXML
    private void addField(){
        if (listSGBD.getSelectionModel().isEmpty()){
            generateAlert("Debe seleccionar una base de datos para crear registros", listSGBD);
            return;
        } else {

        Pane paneField = new Pane();
        paneField.setPrefWidth(tableParent.getPrefWidth() - 100);
        paneField.setPrefHeight(30);
        int tempPositionY = 30;
        if (!tableParent.getChildren().isEmpty()){
            Pane tempPaneField = (Pane) tableParent.getChildren().get(tableParent.getChildren().size() - 1 );
            tempPositionY += tempPaneField.getLayoutY() + tempPaneField.getPrefHeight();
        }

            paneField.setLayoutX((tableParent.getPrefWidth() - paneField.getPrefWidth()) / 2);
            TextField textField = new TextField();
            textField.setPrefWidth(200);
            textField.setPrefHeight(30);
            textField.setPromptText("Nombre del campo");
            textField.setId("textField");
            textField.setOnMouseClicked((event) -> removeBorder(textField));

            CheckBox checkNull = new CheckBox();
            checkNull.setSelected(false);
            checkNull.setText("not Null");
            checkNull.setPrefWidth(65);
            checkNull.setPrefHeight(30);
            checkNull.setLayoutX(textField.getPrefWidth() + 20);
            checkNull.setId("checkNull");
            checkNull.setOnMouseClicked((event) -> removeBorder(checkNull));


            TextField fieldSize = new TextField();
            fieldSize.setPrefWidth(60);
            fieldSize.setPrefHeight(30);
            fieldSize.setPromptText("Tamaño");
            fieldSize.setLayoutX(checkNull.getLayoutX() + checkNull.getPrefWidth() + 20);
            fieldSize.setId("fieldSize");
            fieldSize.setOnMouseClicked((event) -> removeBorder(fieldSize));


            ComboBox dataTypeList = new ComboBox();
            dataTypeList.setPrefWidth(150);
            dataTypeList.setPrefHeight(30);
            dataTypeList.setPromptText("Tipo de dato");
            dataTypeList.setLayoutX(fieldSize.getLayoutX() + fieldSize.getPrefWidth() + 20);
            dataTypeList.setId("dataTypeList");
            dataTypeList.setItems(ComboBoxdataTypeList);
            dataTypeList.setOnMouseClicked((event) -> removeBorder(dataTypeList));


            paneField.getChildren().add(dataTypeList);
            paneField.getChildren().add(fieldSize);
            paneField.getChildren().add(checkNull);
            paneField.getChildren().add(textField);
            paneField.setLayoutY(tempPositionY);
            tableParent.getChildren().add(paneField);
        }
        }



    @FXML
    private void removeField(){
        if (tableParent.getChildren().isEmpty()){return; }
        tableParent.getChildren().removeAll(tableParent.getChildren().get(tableParent.getChildren().size() - 1));

}

    @FXML
    private void createTable(){
        if (tableName.getText().isEmpty()){
            generateAlert("La tabla debe tener un nombre", tableName);
        } else {
            ObservableList<TableObject> tableObject = FXCollections.observableArrayList();
            for (Node fieldPane : tableParent.getChildren()) {
                Pane fieldValues = (Pane) fieldPane;
                TableObject field = new TableObject();
                for (Node fieldValue : fieldValues.getChildren()) {

                    switch (fieldValue.getId()) {
                        case "textField":
                            TextField tempTextField = (TextField) fieldValue;
                            if (!tempTextField.getText().isEmpty()) {
                                field.setNameField(tempTextField.getText());
                            } else {
                                //Alerta y return
                                generateAlert("El nombre de los campos no puede estar vacio", tempTextField);
                                return;
                            }
                            break;
                        case "checkNull":
                            CheckBox tempCheckBox = (CheckBox) fieldValue;
                            if (tempCheckBox.isSelected()) {
                                field.setNull("NOT NULL");
                            } else {
                                field.setNull("NULL");
                            }

                            break;
                        case "fieldSize":
                            TextField tempTampField = (TextField) fieldValue;

                            if (!tempTampField.getText().isEmpty()) {

                                if (tempTampField.getText().matches("[+-]?\\d*(\\.\\d+)?")) {
                                    field.setTamField(Integer.parseInt(tempTampField.getText()));
                                } else {
                                    generateAlert("El tamaño debe estar en numeros", tempTampField);
                                    return;
                                }
                            } else {
                                generateAlert("El tamaño no puede estar vacío", tempTampField);
                                return;
                            }


                            break;
                        case "dataTypeList":
                            ComboBox tempDataTypeList = (ComboBox) fieldValue;
                            if (tempDataTypeList.getSelectionModel().isEmpty()) {
                                generateAlert("Debe seleccionar un tipo de dato", tempDataTypeList);
                                return;
                            } else {
                                field.setTypeField((String) tempDataTypeList.getSelectionModel().getSelectedItem());


                            }
                            break;
                        default:
                            break;

                    }


                }
                tableObject.add(field);

            }
            String primaryKey = "";
           DBAdapter dbAdapter = null;
            switch((String)listSGBD.getSelectionModel().getSelectedItem()){
                case "MySQL":
                    primaryKey = "Id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,";
                    dbAdapter = DBFactory.getDBAdapter(DBType.MySQL);
                    break;
                case "PostgreSQL":
                    primaryKey = "Id SERIAL NOT NULL PRIMARY KEY,";
                    dbAdapter = DBFactory.getDBAdapter(DBType.PostgreSQL);

                    break;
                default:
                    break;
            }

           String auxComma = ",";
           String sqlSecuence = "CREATE TABLE" + " " + tableName.getText() + "("  + primaryKey;

          for (int i = 0; i < tableObject.size(); i++) {
                if (i == tableObject.size() - 1){
                    auxComma = "";
                }
              sqlSecuence = sqlSecuence + tableObject.get(i).generateField()  + auxComma;
            }
          sqlSecuence = sqlSecuence + ")";
            Connection connection = dbAdapter.getConnection();
            Statement statement = null;

            try {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.execute(sqlSecuence);

            } catch (SQLException e) {
                DBUtils.processException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    DBUtils.processException(e);
                }
            }

        }
    }

    private void generateAlert(String infoAlert, Node nodeChange){
        nodeChange.setStyle("-fx-border-color: red;");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(infoAlert);
        alert.setTitle("ERROR");
        alert.show();


    }

    private void removeBorder(Node nodeChange){
        nodeChange.setStyle("-fx-border-color: none;");
    }


}
