package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AllContactsPaneLoader {
    public static AnchorPane loadAllContactsAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AllContactsPaneLoader.class.getResource("/views/AllContactsPane.fxml"));
        try {
            return fxmlLoader.load();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
