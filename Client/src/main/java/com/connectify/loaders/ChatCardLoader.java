package com.connectify.loaders;

import com.connectify.controller.ChatCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ChatCardLoader {

    //private static final Map<AnchorPane,ChatCardController> chatsCardsControllersMap = new HashMap<>();
    public static AnchorPane loadChatCardAnchorPane(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChatCardController controller = new ChatCardController();
        fxmlLoader.setLocation(ChatCardLoader.class.getResource("/views/ChatCardPane.fxml"));
        try {
            AnchorPane pane = fxmlLoader.load();
            //chatsCardsControllersMap.put(pane,controller);
            return pane;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public static ChatCardController getChatsCardController(AnchorPane pane){
//        if(chatsCardsControllersMap.containsKey(pane))
//            return chatsCardsControllersMap.get(pane);
//        return null;
//    }

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
