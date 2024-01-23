package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChatLoader {
    public static BorderPane loadChatPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ChatLoader.class.getResource("/views/Chat.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
