package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LogoLoader {
    public static AnchorPane loadLogoAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(LogoLoader.class.getResource("/views/logoPane.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
