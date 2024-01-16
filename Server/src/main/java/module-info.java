module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    exports com.connectify.controller.fxmlcontrollers;
    exports com.connectify;
    exports com.connectify.loaders;
    opens com.connectify.controller.fxmlcontrollers;
    opens com.connectify;
    opens com.connectify.loaders;
    exports com.connectify.controller.utils;
    opens com.connectify.controller.utils;
}