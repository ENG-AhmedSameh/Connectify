module com.connectify.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.connectify.client to javafx.fxml;
    exports com.connectify.client;
}