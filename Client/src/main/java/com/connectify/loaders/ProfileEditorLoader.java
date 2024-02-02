package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProfileEditorLoader {
    public static AnchorPane loadProfileEditorAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProfileEditorLoader.class.getResource("/views/EditeProfilePane.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
