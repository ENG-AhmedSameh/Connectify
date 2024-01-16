module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports com.connectify.controller.fxmlcontrollers;
    exports com.connectify;
    exports com.connectify.loaders;
    opens com.connectify.controller.fxmlcontrollers;
    opens com.connectify;
    opens com.connectify.loaders;
}