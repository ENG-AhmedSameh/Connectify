package com.connectify.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChatCardLoader {
    public static AnchorPane loadChatCardAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ChatCardLoader.class.getResource("/views/ChatCardPane.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
