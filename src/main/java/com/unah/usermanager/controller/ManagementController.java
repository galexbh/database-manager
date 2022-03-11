package com.unah.usermanager.controller;

import com.unah.usermanager.model.userModel;
import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBType;
import com.unah.usermanager.utils.DBUtils;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {

    @FXML
    public HBox hbxContTable;

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

    Statement statement = null;
    ResultSet resultSet = null;
    FXMLLoader fxmlLoader = null;

    static public DBType dbtype = null;

    Alert alert = new Alert(Alert.AlertType.NONE);
    ObservableList<userModel> userList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxDBType.setItems(FXCollections.observableArrayList(DBType.values()));
        cbxDBType.getItems().setAll(DBType.values());
    }

    public void onGetUsers(ActionEvent actionEvent) {

        switch (cbxDBType.getValue()) {

            case MySQL, MariaDB -> getUsersMySQL();

            case PostgreSQL -> getUsersPostgreSQL();

        }

    }

    public ObservableList<userModel> getDataUsersMySQL() throws SQLException {
        DBAdapter dbAdapter = DBFactory.getDBAdapter(cbxDBType.getValue());
        ObservableList<userModel> list = FXCollections.observableArrayList();
        Connection connection = dbAdapter.getConnection();

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery("select * from mysql.user");

        while (resultSet.next()) {
            list.add(new userModel(
                    resultSet.getString("User"),
                    resultSet.getString("Select_priv"),
                    resultSet.getString("Insert_priv"),
                    resultSet.getString("Update_priv"),
                    resultSet.getString("Delete_priv"),
                    resultSet.getString("Create_priv"),
                    resultSet.getString("Drop_priv")));
        }

        try {
            connection.close();
        } catch (SQLException e) {
            DBUtils.processException(e);
        }

        return list;
    }

    public void getUsersMySQL() {
        colName.setCellValueFactory(new PropertyValueFactory<>("User"));
        colSelect.setCellValueFactory(new PropertyValueFactory<>("Select_priv"));
        colInsert.setCellValueFactory(new PropertyValueFactory<>("Insert_priv"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("Update_priv"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("Delete_priv"));
        colCreate.setCellValueFactory(new PropertyValueFactory<>("Create_priv"));
        colDrop.setCellValueFactory(new PropertyValueFactory<>("Drop_priv"));

        try {
            userList = getDataUsersMySQL();
            tableUser.setItems(userList);
        } catch (SQLException e) {
            DBUtils.processException(e);
        }

    }

    public void getUsersPostgreSQL() {

    }

    public void onChangePrivileges(ActionEvent actionEvent) throws IOException {

        try {
            dbtype = cbxDBType.getValue();

            switch (cbxDBType.getValue()) {
                case MySQL, MariaDB -> fxmlLoader = loadForm("/com/unah/usermanager/privileges-mysql-view.fxml");
                case PostgreSQL -> fxmlLoader = loadForm("/com/unah/usermanager/privileges-pg-view.fxml");
            }

            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("User Manager");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            DBUtils.processNullException(e);
        }
    }

    public void onAddUser(ActionEvent actionEvent) throws IOException {
        try {
            dbtype = cbxDBType.getValue();

            switch (cbxDBType.getValue()) {
                case MySQL, MariaDB, PostgreSQL -> fxmlLoader = loadForm("/com/unah/usermanager/add-user-view.fxml");
            }

            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("User Manager");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            DBUtils.processNullException(e);
        }
    }

    private FXMLLoader loadForm(String url) {
        return new FXMLLoader(getClass().getResource(url));
    }

}

