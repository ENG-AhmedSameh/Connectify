package com.connectify;

import com.connectify.loaders.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Client extends Application {

    @Override
    public void init() throws Exception {
        ViewLoader loader = ViewLoader.getInstance();
        super.init();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ViewLoader loader = ViewLoader.getInstance();
        Scene scene = new Scene(loader.getMainBorderPaneScene());
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
