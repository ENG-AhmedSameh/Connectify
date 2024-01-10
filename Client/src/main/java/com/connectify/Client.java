package com.connectify;

import com.connectify.loaders.ViewLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Client extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        ViewLoader loader = new ViewLoader();
//        Parent root = loader.getMainPane();
//        Scene scene = new Scene(root);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/views/SignUpPane.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(750);
        stage.setMinHeight(500);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
