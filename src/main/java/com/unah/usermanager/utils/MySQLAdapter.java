package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAdapter implements DBAdapter {
    @Override
    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            connection = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/galex_db?" +
                            "user=galex_user&password=An0thrS3crt");

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