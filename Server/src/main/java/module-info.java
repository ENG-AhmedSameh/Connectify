module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires java.desktop;
    requires c3p0;
    requires java.rmi;
    requires com.connectify.shared;
    exports com.connectify.controller.fxmlcontrollers;
    exports com.connectify.loaders;
    opens com.connectify.controller.fxmlcontrollers;
    opens com.connectify.loaders;
    exports com.connectify.utils;
    opens com.connectify.utils;
    exports com.connectify.controller;
    opens com.connectify.controller;
    exports com.connectify.app;
    opens com.connectify.app;
}