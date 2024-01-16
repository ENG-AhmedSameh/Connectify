module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
<<<<<<< HEAD
    requires mysql.connector.j;
    requires java.naming;
=======
>>>>>>> ead32fea8241abc3703f0fa142e33015795735b0
    exports com.connectify.controller.fxmlcontrollers;
    exports com.connectify;
    exports com.connectify.loaders;
    opens com.connectify.controller.fxmlcontrollers;
    opens com.connectify;
    opens com.connectify.loaders;
    exports com.connectify.controller.utils;
    opens com.connectify.controller.utils;
}