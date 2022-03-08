package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;
import javafx.scene.control.Alert;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MSSQLAdapter implements DBAdapter {
    @Override
    public Connection getConnection() {

        Alert alert = new Alert(Alert.AlertType.NONE);
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();

            connection = DBUtils.getConnection(DBType.MSSQL);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection established");
            alert.show();

        } catch (
                InvocationTargetException |
                        ClassNotFoundException |
                        InstantiationException |
                        IllegalAccessException |
                        NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            DBUtils.processException(e);
        } 

        return connection;
    }

}
