package com.unah.usermanager.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import java.util.HashMap;
import java.util.Map;



public class TableViewController {

    @FXML
    private TableView<ObservableList<Map.Entry<String,String>>> tableResult;
    @FXML
    private TextField countColumns;
    @FXML
    private void createTable(){


        String[] columnsNames = {"colId", "col1", "col2"};
        ObservableList<String[]> values = FXCollections.observableArrayList();
        String[] dats = {"w", "a", "a"};


        String[] dats2 = {"q", "b", "b"};
        String[] dats3 = {"s", "c", "x"};
        values.add(dats);
        values.add(dats2);
        values.add(dats3);

        Map[] all = new Map[values.size()];
        for (int i = 0; i < columnsNames.length; i ++){
            Map<String, String> temMap = new HashMap<>();
            all[i] = temMap;
        }

        for(int i = 0; i < values.size(); i++){
            String[] tempCamps = values.get(i);
            for (int k = 0; k < tempCamps.length; k++){
                all[i].put(String.valueOf(k), tempCamps[k]);
            }
        }
        ObservableList <ObservableList<Map.Entry<String, String>>> allItems = FXCollections.observableArrayList();
        for (Map item: all){
            System.out.println(item.toString());
            ObservableList<Map.Entry<String, String>> tempItem = FXCollections.observableArrayList(item.entrySet());
            allItems.add(tempItem);
        }

    for (int i = 0; i < columnsNames.length; i++){
        TableColumn<ObservableList<Map.Entry<String, String>>, String> columnTemp = new TableColumn<>(columnsNames[i]);
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
}
