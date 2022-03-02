package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MSSQLAdapter implements DBAdapter {
    @Override
    public Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();

            String hostname = "localhost";
            String sqlInstanceName = ""; //computer name
            String sqlDatabase = "";  //sql server database name
            String sqlUser = "sa";
            String sqlPassword = ""; //passwrod sa account

            String connectURL = "jdbc:sqlserver://" + hostname + ":1433"
                    + ";instance=" + sqlInstanceName + ";databaseName=" + sqlDatabase;

            connection = DriverManager.getConnection(connectURL, sqlUser, sqlPassword);

            System.out.println("Conecto");

        } catch (SQLException |
                InvocationTargetException |
                ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return connection;
    }

}
