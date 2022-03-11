package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class TableViewController implements Initializable {

    public static ObservableList<String>  columnsNames = FXCollections.observableArrayList();
    public static ObservableList<String> selectedField  = FXCollections.observableArrayList();
    public static ObservableList<String>  columnsType = FXCollections.observableArrayList();
    public static String tableValue = "";
    public static String dataBase = "";

    @FXML
    private TableView<ObservableList<Map.Entry<String,String>>> tableResult;
    @FXML
    private ComboBox tablesList;

    @FXML
    private ComboBox SGBDComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SGBDComboBox.setOnMouseClicked((event) -> removeBorder(SGBDComboBox));
        tableResult.setOnMouseClicked((event) -> removeBorder(tableResult));
        SGBDComboBox.setItems(FXCollections.observableArrayList("MySQL", "PostgreSQL", "MariaDB"));
    }

    @FXML
    private void readTable() {
        if (tablesList.getSelectionModel().isEmpty()){
            return;
        }
        tableResult.getColumns().clear();
        String table =(String) tablesList.getSelectionModel().getSelectedItem();
        DBAdapter dbAdapter = getDBAdapter();
        Connection connection = dbAdapter.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String getColumns = "SELECT COLUMN_NAME FROM Information_Schema.Columns WHERE TABLE_NAME = \"" + table + "\"" + " ORDER BY ORDINAL_POSITION";
        String getFilds = "SELECT * FROM " + table;
        ObservableList<String> columnsNames =  FXCollections.observableArrayList();
        ObservableList<String[]> fields = FXCollections.observableArrayList();
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(getColumns);

            while (resultSet.next()) {
                columnsNames.add((String) resultSet.getObject("COLUMN_NAME"));
            }

            resultSet = statement.executeQuery(getFilds);

            while(resultSet.next()) {
                String[] tempField = new String[columnsNames.size()];
                for (int i = 0; i < columnsNames.size(); i++) {
                    tempField[i] = String.valueOf(resultSet.getObject(columnsNames.get(i)));
                }
                fields.add(tempField);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        Map[] all = new Map[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Map<String, String> temMap = new HashMap<>();
            all[i] = temMap;
        }

        for (int i = 0; i < fields.size(); i++) {
            String[] tempCamps = fields.get(i);
            for (int k = 0; k < tempCamps.length; k++) {
                all[i].put(String.valueOf(k), tempCamps[k]);
            }
        }
        ObservableList<ObservableList<Map.Entry<String, String>>> allItems = FXCollections.observableArrayList();
        for (Map item : all) {
            ObservableList<Map.Entry<String, String>> tempItem = FXCollections.observableArrayList(item.entrySet());
            allItems.add(tempItem);
        }

        for (int i = 0; i < columnsNames.size(); i++) {
            TableColumn<ObservableList<Map.Entry<String, String>>, String> columnTemp = new TableColumn<>(columnsNames.get(i));
            int finalI = i;
            columnTemp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<Map.Entry<String, String>>, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<Map.Entry<String, String>>, String> p) {
                    // for second column we use value
                    return new SimpleStringProperty(p.getValue().get(finalI).getValue());
                }
            });


            tableResult.getColumns().add(columnTemp);


        }
        tableResult.setItems(allItems);

    }


    @FXML
    private void getTables() {

        DBAdapter dbAdapter = getDBAdapter();
        Connection connection = dbAdapter.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ObservableList<String> tablesInDatabase = FXCollections.observableArrayList();
        String query = "SHOW tables";
        if ((String)SGBDComboBox.getSelectionModel().getSelectedItem() == "PostgreSQL"  ){
            query = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name";
        }
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("SHOW tables");

            while(resultSet.next()) {
                tablesInDatabase.add((String) resultSet.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            tablesList.setItems(tablesInDatabase);
    }

    @FXML
    private void verificSelection(){
        if (SGBDComboBox.getSelectionModel().isEmpty()) {
            generateAlert("Debe seleccinar una tabla", SGBDComboBox);
            tablesList.setStyle("-fx-border-color: none;");
            return;
        }


    }
    private void generateAlert(String infoAlert, Node nodeChange){
        nodeChange.setStyle("-fx-border-color: red;");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(infoAlert);
        alert.setTitle("ERROR");
        alert.show();


    }
    private void generateAlert(String infoAlert){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(infoAlert);
        alert.setTitle("ERROR");
        alert.show();


    }

    @FXML
    private void deleteTable(){

        if (tablesList.getSelectionModel().isEmpty()) {
            generateAlert("Debe seleccinar una base de datos", SGBDComboBox);
            return;
        }

        String table = (String) tablesList.getSelectionModel().getSelectedItem();
        DBAdapter dbAdapter = getDBAdapter();
        Connection connection = dbAdapter.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;


        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.execute(" DROP TABLE " + table);
            tableResult.getColumns().clear();
            tablesList.getItems().clear();
            getTables();




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void deleteField(){
        if (tablesList.getSelectionModel().isEmpty()){
            generateAlert("Es necesario seleccionar una tabla", tablesList);
            return;
        }
        if (tableResult.getItems().isEmpty()){
            generateAlert("La tabla no contiene elementos, no es posible eliminar");
            return;
        }
        if (tableResult.getSelectionModel().isEmpty()) {
            generateAlert("Es necesario seleccionar un elemento");
        } else {
            ObservableList<Map.Entry<String,String>> selectedField = tableResult.getSelectionModel().getSelectedItem();

            int idField = Integer.valueOf(selectedField.get(0).getValue());
            DBAdapter dbAdapter = getDBAdapter();
            Connection connection = dbAdapter.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;


            try {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.execute("DELETE FROM " + tablesList.getSelectionModel().getSelectedItem() + " WHERE Id = " + idField);

            } catch (SQLException e) {
                e.printStackTrace();
            }
                getTables();
        }



    }

    @FXML
    private void insertField(){
        columnsType = FXCollections.observableArrayList();
        columnsNames = FXCollections.observableArrayList();
        tableValue ="";
        dataBase = "";
        if (tablesList.getSelectionModel().isEmpty()){
            generateAlert("Es necesario seleccionar una tabla", tablesList);
            return;
        }
        DBAdapter dbAdapter = getDBAdapter();
        Connection connection = dbAdapter.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        String tableName = (String) tablesList.getSelectionModel().getSelectedItem();
        String selectItems = "SELECT * FROM " + tableName;
        String getColumns = "SELECT COLUMN_NAME FROM Information_Schema.Columns WHERE TABLE_NAME = \"" + tableName + "\"" + " ORDER BY ORDINAL_POSITION";


        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(getColumns);


            while (resultSet.next()) {
                columnsNames.add((String) resultSet.getObject("COLUMN_NAME"));
            }
            resultSet = statement.executeQuery(selectItems);
            for (int i =0; i < columnsNames.size(); i++) {
                columnsType.add(resultSet.getMetaData().getColumnTypeName(i + 1));
            }
            tableValue = tableName;
            dataBase = (String) SGBDComboBox.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = null;
            fxmlLoader = loadForm("/com/unah/usermanager/insertField-view.fxml");

            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inserta campo");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void removeBorder(Node nodeChange){
        nodeChange.setStyle("-fx-border-color: none;");
    }

    private DBAdapter getDBAdapter(){
        DBAdapter dbAdapter = null;
        switch ((String) SGBDComboBox.getSelectionModel().getSelectedItem()) {
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
        return dbAdapter;

    }

    @FXML
    private void newTable(){
        FXMLLoader fxmlLoader = null;
        fxmlLoader = loadForm("/com/unah/usermanager/create-table-view.fxml");

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Crear tabla");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void updateField(){
        columnsType = FXCollections.observableArrayList();
        columnsNames = FXCollections.observableArrayList();
        selectedField = FXCollections.observableArrayList();
        tableValue ="";
        dataBase = "";


        if (tableResult.getItems().isEmpty()){
            generateAlert("La tabla no tiene elementos para editar");
            return;
        }
        if (tableResult.getSelectionModel().isEmpty()){
            generateAlert("Debe seleccionar un elemento");
            return;
        }
        ObservableList<Map.Entry<String,String>> columns = tableResult.getSelectionModel().getSelectedItem();
        for (Map.Entry<String,String> field: columns){
            selectedField.add(field.getValue());
        }

        DBAdapter dbAdapter = getDBAdapter();
        String queryToGet = "SELECT*FROM " + tablesList.getSelectionModel().getSelectedItem() + " WHERE Id =" + selectedField.get(0);
        String getColumns = "SELECT COLUMN_NAME FROM Information_Schema.Columns WHERE TABLE_NAME = \"" + tablesList.getSelectionModel().getSelectedItem() + "\"" + " ORDER BY ORDINAL_POSITION";

        Connection connection = dbAdapter.getConnection();
        System.out.println(connection);
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(getColumns);
            while (resultSet.next()) {
                columnsNames.add((String) resultSet.getObject("COLUMN_NAME"));
            }

            resultSet = statement.executeQuery(queryToGet);
            for (int i =0; i < columnsNames.size(); i++) {
                columnsType.add(resultSet.getMetaData().getColumnTypeName(i + 1));
            }
            tableValue = (String) tablesList.getSelectionModel().getSelectedItem();
            dataBase = (String) SGBDComboBox.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = null;
            fxmlLoader = loadForm("/com/unah/usermanager/updateField-view.fxml");

            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Editar campo");


            stage.setScene(new Scene(root));
            stage.show();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    private void updateTable(){
        readTable();
    }

    @FXML
    private void updateTables(){
        if (SGBDComboBox.getSelectionModel().isEmpty()) {
        generateAlert("Debe seleccinar una base de datos", SGBDComboBox);
        return;
    }

        getTables();
    }

    private FXMLLoader loadForm(String url) {
        return new FXMLLoader(getClass().getResource(url));
    }

}

