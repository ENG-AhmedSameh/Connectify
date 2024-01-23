package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TitleBarLoader {
    public static HBox loadTitleBarHBox(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(TitleBarLoader.class.getResource("/views/titleBarPane.fxml"));
        try {
            return fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
