package com.unah.usermanager.controller;

import com.unah.usermanager.utils.DBFactory;
import com.unah.usermanager.utils.DBUtils;
import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.unah.usermanager.controller.ManagementController.dbtype;
import static com.unah.usermanager.utils.DBType.MySQL;

public class PrivilegesMysqlController implements Initializable {

    public ComboBox<String> cbxTable;
    @FXML
    private ComboBox<String> cbxUser;

    @FXML
    private CheckBox chbxCreate;

    @FXML
    private CheckBox chbxDelete;

    @FXML
    private CheckBox chbxDrop;

    @FXML
    private CheckBox chbxInsert;

    @FXML
    private CheckBox chbxSelect;

    @FXML
    private CheckBox chbxUpdate;

    @FXML
    private CheckBox chbxAll;

    @FXML
    private ListView<String> lsvPriv;

    Statement statementUser = null;
    Statement statementTable = null;
    ResultSet resultSetUser = null;
    ResultSet resultSetTables = null;
    DBAdapter dbAdapter = DBFactory.getDBAdapter(MySQL);

    @FXML
    void onGrantPrivileges(ActionEvent event) {
        if (!chbxAll.isSelected()) {
            privilegeQuery("GRANT ");
        } else {
            allPrivilegeQuery("GRANT ");
        }
    }

    @FXML
    void onRevokePrivileges(ActionEvent event) {
        if (!chbxAll.isSelected()){
            privilegeQuery("REVOKE ");
        } else {
            allPrivilegeQuery("REVOKE ");
        }
    }

    @FXML
    void onMouseEventCreate(MouseEvent event) {
        selectedEvent(chbxCreate.isSelected(), "CREATE");
    }

    @FXML
    void onMouseEventDelete(MouseEvent event) {
        selectedEvent(chbxDelete.isSelected(), "DELETE");
    }

    @FXML
    void onMouseEventDrop(MouseEvent event) {
        selectedEvent(chbxDrop.isSelected(), "DROP");
    }

    @FXML
    void onMouseEventInsert(MouseEvent event) {
        selectedEvent(chbxInsert.isSelected(), "INSERT");
    }

    @FXML
    void onMouseEventSelect(MouseEvent event) {
        selectedEvent(chbxSelect.isSelected(), "SELECT");
    }

    @FXML
    void onMouseEventUpdate(MouseEvent event) {
        selectedEvent(chbxUpdate.isSelected(), "UPDATE");
    }

    @FXML
    void onMouseEventAll(MouseEvent event) {
        lsvPriv.getItems().clear();
        selectedEvent(chbxAll.isSelected(), "ALL PRIVILEGES");
        chbxSelect.setSelected(false);
        chbxInsert.setSelected(false);
        chbxDelete.setSelected(false);
        chbxUpdate.setSelected(false);
        chbxCreate.setSelected(false);
        chbxDrop.setSelected(false);
    }

    private void selectedEvent(boolean checkbox, String permission) {
        try {
            if (checkbox) {
                lsvPriv.getItems().add(permission);
            } else {
                for (int index = 0; index <= lsvPriv.getItems().size(); index++) {
                    if (lsvPriv.getItems().get(index).equals(permission)) {
                        lsvPriv.getItems().remove(index);
                    }
                }
            }
        } catch (Exception ignore) {
        }
    }

    private void allPrivilegeQuery(String query) {
        Connection connection = dbAdapter.getConnection();

        query += getPrivileges();

        try {
            statementUser = connection.createStatement();

            switch (dbtype) {
                case MySQL -> statementUser.execute(query + " ON " + DBUtils.MYSQL_DB + ".* TO " + cbxUser.getValue());
                case MariaDB -> statementUser.execute(query + " ON " + DBUtils.MARIADB_DB + ".* TO " + cbxUser.getValue());
            }

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

    private void privilegeQuery(String query) {
        Connection connection = dbAdapter.getConnection();

        query += getPrivileges();

        try {
            statementUser = connection.createStatement();
            statementUser.execute(query + " ON " + cbxTable.getValue() + " TO " + cbxUser.getValue());
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

    private String getPrivileges() {
        String privileges = "";

        for (int index = 0; index <= lsvPriv.getItems().size() - 1; index++) {
            System.out.println(lsvPriv.getItems().get(index));
            privileges += lsvPriv.getItems().get(index) + ",";
        }

        return privileges.substring(0, privileges.length() - 1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listUsers = FXCollections.observableArrayList();
        ObservableList<String> listTables = FXCollections.observableArrayList();
        Connection connection = dbAdapter.getConnection();

        try {
            statementUser = connection.createStatement();
            statementTable = connection.createStatement();
            resultSetUser = statementUser.executeQuery("select * from mysql.user");
            resultSetTables = statementTable.executeQuery("SHOW TABLES");

            while (resultSetUser.next()) {
                listUsers.add(resultSetUser.getString("User"));
            }

            while (resultSetTables.next()) {
                listTables.add(resultSetTables.getString(1));
            }
        } catch (SQLException e) {
            DBUtils.processException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                DBUtils.processException(e);
            }
        }

        cbxUser.setItems(FXCollections.observableArrayList(listUsers));
        cbxUser.getItems().setAll(listUsers);

        cbxTable.setItems(FXCollections.observableArrayList(listTables));
        cbxTable.getItems().setAll(listTables);
    }
}
