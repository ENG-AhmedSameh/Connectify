package com.connectify.loaders;

import com.connectify.controller.ChatCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Timestamp;

public class ChatCardLoader {
    public static AnchorPane loadChatCardAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChatCardController controller = new ChatCardController();
        fxmlLoader.setLocation(ChatCardLoader.class.getResource("/views/ChatCardPane.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane loadChatCardAnchorPane(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChatCardController controller = new ChatCardController(chatId,unread,name,picture,lastMessage,timestamp);
        fxmlLoader.setLocation(ChatCardLoader.class.getResource("/views/ChatCardPane.fxml"));
        fxmlLoader.setController(controller);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
