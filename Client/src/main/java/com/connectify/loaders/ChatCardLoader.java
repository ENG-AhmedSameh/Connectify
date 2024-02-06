package com.connectify.loaders;

import com.connectify.controller.ChatCardController;
import com.connectify.utils.ChatManager;
import com.connectify.utils.ChatManagerFactory;
import com.connectify.utils.CurrentUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ChatCardLoader {

    private static final Map<AnchorPane,Integer> chatsCardsIdMap = new HashMap<>();
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
    public static Integer getChatsCardId(AnchorPane pane){
        if(chatsCardsIdMap.containsKey(pane))
            return chatsCardsIdMap.get(pane);
        return null;
    }

    public static AnchorPane loadChatCardAnchorPane(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ChatCardController controller = new ChatCardController(chatId,unread,name,picture,lastMessage,timestamp);
        fxmlLoader.setLocation(ChatCardLoader.class.getResource("/views/ChatCardPane.fxml"));
        fxmlLoader.setController(controller);
        ChatManager chatManager = CurrentUser.getChatManagerFactory().getChatManager(chatId);
        chatManager.setChatCardController(controller);
        try {
            AnchorPane chatCardPane = fxmlLoader.load();
            chatsCardsIdMap.put(chatCardPane,chatId);
            chatManager.setChatCardPane(chatCardPane);
            return chatCardPane;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
