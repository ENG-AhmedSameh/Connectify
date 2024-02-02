package com.connectify.loaders;

import com.connectify.controller.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChatLoader {
    public static BorderPane loadChatPane(int chat_id,String name, Image picture) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChatController controller = new ChatController(chat_id,name,picture);
        fxmlLoader.setLocation(ChatLoader.class.getResource("/views/Chat.fxml"));
        fxmlLoader.setController(controller);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
