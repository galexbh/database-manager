package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLAdapter implements DBAdapter {
    @Override
    public Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            connection = DBUtils.getConnection(DBType.MySQL);

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