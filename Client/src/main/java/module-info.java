module Client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.connectify;
    exports com.connectify;
}