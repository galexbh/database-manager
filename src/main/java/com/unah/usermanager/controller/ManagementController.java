package com.unah.usermanager.controller;

import com.unah.usermanager.model.userModel;
import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {

    @FXML
    private ComboBox<DBType> cbxDBType;

    @FXML
    private TableColumn<userModel, String> colCreate;

    @FXML
    private TableColumn<userModel, String> colDelete;

    @FXML
    private TableColumn<userModel, String> colDrop;

    @FXML
    private TableColumn<userModel, String> colInsert;

    @FXML
    private TableColumn<userModel, String> colName;

    @FXML
    private TableColumn<userModel, String> colSelect;

    @FXML
    private TableColumn<userModel, String> colUpdate;

    @FXML
    private TableView<userModel> tableUser;

    static Statement statement = null;
    static ResultSet resultSet = null;

    ObservableList<userModel> userList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxDBType.setItems(FXCollections.observableArrayList(DBType.values()));
        cbxDBType.getItems().setAll(DBType.values());

    }

    public void onGetUsers(ActionEvent actionEvent) throws SQLException {


        colName.setCellValueFactory(new PropertyValueFactory<userModel,String>("User"));
        colSelect.setCellValueFactory(new PropertyValueFactory<userModel,String>("Select_priv"));
        colInsert.setCellValueFactory(new PropertyValueFactory<userModel,String>("Insert_priv"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<userModel,String>("Update_priv"));
        colDelete.setCellValueFactory(new PropertyValueFactory<userModel,String>("Delete_priv"));
        colCreate.setCellValueFactory(new PropertyValueFactory<userModel,String>("Create_priv"));
        colDrop.setCellValueFactory(new PropertyValueFactory<userModel,String>("Drop_priv"));

        try {
            userList = getDataUsers();
            tableUser.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<userModel> getDataUsers() throws SQLException {
        DBAdapter dbAdapter = DBFactory.getDBAdapter(cbxDBType.getValue());
        ObservableList<userModel> list = FXCollections.observableArrayList();
        Connection connection = dbAdapter.getConnection();

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery("select * from mysql.user");

        while (resultSet.next()){
            list.add(new userModel(
                    resultSet.getString("User"),
                    resultSet.getString("Select_priv"),
                    resultSet.getString("Insert_priv"),
                    resultSet.getString("Update_priv"),
                    resultSet.getString("Delete_priv"),
                    resultSet.getString("Create_priv"),
                    resultSet.getString("Drop_priv")));
        }

        return list;
    }
}