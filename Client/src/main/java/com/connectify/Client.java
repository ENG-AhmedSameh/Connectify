package com.connectify;

import com.connectify.loaders.ViewLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchToLogin();
        Scene scene = new Scene(loader.getMainBorderPane());
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(750);
        stage.setMinHeight(500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
