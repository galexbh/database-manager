package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLAdapter implements DBAdapter {
    @Override
    public Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();

            connection = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5438/mydb?" +
                            "user=galex&password=mssql1Ipw");

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
