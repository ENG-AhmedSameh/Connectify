package com.connectify.app;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.utils.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class Client extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        StageManager.getInstance().switchToLogin();
        primaryStage.show();
        String autoLogin = PropertiesManager.getInstance().getAutoLogin();
    if(!RemoteManager.getInstance().isServerDown() && autoLogin != null && autoLogin.equals("true")){
            ConnectedUser user = CurrentUser.getInstance();
            if(user.getPhoneNumber() != null){
                RemoteManager.getInstance().registerConnectedUser(user);
                StageManager.getInstance().switchToHome();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
