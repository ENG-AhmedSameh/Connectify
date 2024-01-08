package com.connectify;

import com.connectify.loaders.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Server extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewLoader loader = new ViewLoader();
        Parent root = loader.getMainPane();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}