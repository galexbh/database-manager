package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;

import java.sql.Connection;

public class MySQLAdapter implements DBAdapter {
    @Override
    public Connection getConnection(){
        Connection connection = null;
        return connection;
    }
}