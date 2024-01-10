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
    public void start(Stage stage) throws IOException {
        ViewLoader loader = ViewLoader.getInstance();
        BorderPane mainPane = loader.getMainBorderPane();
        GridPane centerPane =(GridPane)mainPane.getCenter();
        centerPane.add(loader.getLogoAnchorPane(),0,0);
        centerPane.add(loader.getSignUpAnchorPane(),1,0);
        //mainPane.setTop(loader.getTitleBarHBox());
        mainPane.setCenter(centerPane);
        Parent root = mainPane;
        Scene scene = new Scene(root);
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
