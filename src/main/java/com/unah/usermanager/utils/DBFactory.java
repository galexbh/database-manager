package com.unah.usermanager.utils;

import com.unah.usermanager.utils.interfaces.DBAdapter;

public class DBFactory {

    public static DBAdapter getDBAdapter(DBType dbType) {
        return switch (dbType) {
            case MySQL, MariaDB -> new MySQLAdapter();
            case PostgreSQL -> new PostgreSQLAdapter();
        };
    }
}
