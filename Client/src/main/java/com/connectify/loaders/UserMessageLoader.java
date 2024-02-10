package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class UserMessageLoader {
    public static HBox loadUserMessageHBox(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(UserMessageLoader.class.getResource("/views/UserMessage.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
