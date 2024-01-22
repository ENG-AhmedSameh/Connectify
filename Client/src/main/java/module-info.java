module Client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;

    opens com.connectify;
    opens com.connectify.loaders;
    opens com.connectify.controller;
    opens com.connectify.utils;
    exports com.connectify;
    exports com.connectify.loaders;
    exports com.connectify.controller;
    exports com.connectify.utils;
}