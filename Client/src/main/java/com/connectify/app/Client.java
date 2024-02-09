package com.connectify.app;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.PropertiesManager;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        primaryStage.setTitle("Connectify");
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
