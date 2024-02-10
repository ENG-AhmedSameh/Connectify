package com.connectify.loaders;

import com.connectify.controller.ChatController;
import com.connectify.utils.ChatManager;
import com.connectify.utils.CurrentUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChatLoader {
    public static BorderPane loadChatPane(int chat_id,String name, byte[] picture) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChatController controller = new ChatController(chat_id,name,picture);
        fxmlLoader.setLocation(ChatLoader.class.getResource("/views/Chat.fxml"));
        fxmlLoader.setController(controller);
        try {
            BorderPane chatPane = fxmlLoader.load();
            ChatManager chatManager = CurrentUser.getChatManagerFactory().getChatManager(chat_id);
            chatManager.setChatPane(chatPane);
            chatManager.setChatController(controller);
            return chatPane;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.err.println("Error: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
