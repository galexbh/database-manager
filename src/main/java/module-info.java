module com.unah.usermanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.unah.usermanager to javafx.fxml;
    exports com.unah.usermanager;
    exports com.unah.usermanager.controller;
    opens com.unah.usermanager.controller to javafx.fxml;
}