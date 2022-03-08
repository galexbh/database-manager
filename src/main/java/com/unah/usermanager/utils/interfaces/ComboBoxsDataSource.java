package com.unah.usermanager.utils.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface ComboBoxsDataSource {
    ObservableList<String> mysqlDataType = FXCollections.observableArrayList(
            "CHAR",
            "VARCHAR",
            "INTEGER",
            "BINARY",
            "TINYINT",
            "SMALLINT",
            "MEDIUMINT",
            "BIGINT",
            "VARBINARY",
            "TINYBLOB",
            "TINYTEXT",
            "BLOB",
            "TEXT",
            "MEDIUMBLOB",
            "MEDIUMTEXT",
            "LONGBLOB",
            "LONGTEX",
            "ENUM",
            "SET");
    ObservableList<String> mssqlDataType = FXCollections.observableArrayList(

    );
    ObservableList<String> postgresqlDataType = FXCollections.observableArrayList();


}
