package com.unah.usermanager.utils;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    static Alert alert = new Alert(Alert.AlertType.NONE);

    private static String MYSQL_CONNECTION = "jdbc:mysql://localhost:3306/mysqlDB?user=root&password=mauFJcuf5dhRMQrjj";
    private static String POSTGRESQL_CONNECTION = "jdbc:postgresql://localhost:5438/mydb?user=galex&password=mssql1Ipw";

    public static String MYSQL_DB = "galex_db";
    private static String MARIADB_CONNECTION = "";
    public static String MARIADB_DB = "galex_db";
    public static String POSTGRESQL_DB = "";


    public static Connection getConnection(DBType dbType) throws SQLException {
        return switch (dbType) {
            case MySQL -> DriverManager.getConnection(MYSQL_CONNECTION);
            case MariaDB -> DriverManager.getConnection(MARIADB_CONNECTION);
            case PostgreSQL -> DriverManager.getConnection(POSTGRESQL_CONNECTION);
        };
    }

    public static void setMySQLString(String port, String server, String db, String user, String password) {
        MYSQL_CONNECTION = "jdbc:mysql://" + server + ":" + port + "/" + db + "?" + "user=" + user + "&" + "password=" + password;
        MYSQL_DB = db;
    }

    public static void setMariaDBSring(String port, String server, String db, String user, String password) {
        MARIADB_CONNECTION = "jdbc:mysql://" + server + ":" + port + "/" + db + "?" + "user=" + user + "&" + "password=" + password;
        MYSQL_DB = db;
    }

    public static void setPostgreSQLString(String port, String server, String db, String user, String password) {
        POSTGRESQL_CONNECTION = "jdbc:postgresql://" + server + ":" + port + "/" + db + "?" + "user=" + user + "&" + "password=" + password;
        POSTGRESQL_DB = db;
    }

    public static void processException(SQLException e) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText("Error " + e.getMessage());
        alert.setContentText("Code " + e.getErrorCode());
        alert.setContentText("SQL State " + e.getSQLState());
        alert.show();
    }

    public static void processNullException(NullPointerException e) {
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setContentText("Select a database");
        alert.show();
    }
}
