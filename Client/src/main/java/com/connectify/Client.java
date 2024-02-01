package com.connectify;

import com.connectify.loaders.ViewLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client extends Application {

    private final static String host = "localhost";
    private final static int port = 1099;

    public static Registry registry;

    @Override
    public void init() throws Exception {
        super.init();
        registry = LocateRegistry.getRegistry(host, port);
    }

    @Override
    public void start(Stage stage){
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchToLogin();
        Scene scene = new Scene(loader.getMainBorderPane());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(850);
        stage.setMinHeight(500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
