package com.unah.usermanager.utils.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface ComboBoxsDataSource {
    ObservableList<String> mysqlDataType = FXCollections.observableArrayList(
            "TINYINT",
            "SMALLINT",
            "MEDIUMINT",
            "BIGINT",
            "BIT",
            "TINYINT UNSIGNED",
            "SMALLINT UNSIGNED",
            "MEDIUMINT UNSIGNED",
            "INT UNSIGNED",
            "BIGINT UNSIGNED",
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
    ObservableList<String> postgresqlDataType = FXCollections.observableArrayList(


    );


}
