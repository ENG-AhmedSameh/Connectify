package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainPaneLoader {
    public static BorderPane loadMainBorderPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainPaneLoader.class.getResource("/views/MainPane.fxml"));
        try {
            return fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
