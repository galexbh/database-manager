package com.unah.usermanager.utils;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    static Alert alert = new Alert(Alert.AlertType.NONE);
    private static String MYSQL_CONNECTION = "";
    private static String MSSQL_CONNECTION = "";
    private static String POSTGRESQL_CONNECTION = "";

    public static Connection getConnection(DBType dbType) throws SQLException {
        return switch (dbType) {
            case MySQL -> DriverManager.getConnection(MYSQL_CONNECTION);
            case MSSQL -> DriverManager.getConnection(MSSQL_CONNECTION);
            case PostgreSQL -> DriverManager.getConnection(POSTGRESQL_CONNECTION);
        };
    }

    public static void setMySQLString(String port, String server, String db, String user, String password) {
        MYSQL_CONNECTION = "jdbc:mysql://" + server + ":" + port + "/" + db + "?" + "user=" + user + "&" + "password=" + password;
    }

    public static void setMSSQLString(String port, String instance, String server, String db, String user, String password) {
        MSSQL_CONNECTION = "jdbc:sqlserver://" + server + ":" + port + ";instance=" + instance + ";databaseName=" + db+ "userName=" + user + "password=" + password;
    }

    public static void setPostgreSQLString(String port, String server, String db, String user, String password) {
        POSTGRESQL_CONNECTION = "jdbc:postgresql://" + server + ":" + port + "/" + db + "?" + "user=" + user + "&" + "password=" + password;
    }

    public static void processException(SQLException e) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText("Error " + e.getMessage());
        alert.setContentText("Code " + e.getErrorCode());
        alert.setContentText("SQL State " + e.getSQLState());
        alert.show();
    }
}
