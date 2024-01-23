package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginLoader {
    public static AnchorPane loadLoginAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(LoginLoader.class.getResource("/views/LoginPane.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
