module Server {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.connectify.fxmlcontrollers;
    exports com.connectify;
    exports com.connectify.loaders;
    opens com.connectify.fxmlcontrollers;
    opens com.connectify;
    opens com.connectify.loaders;
}