package com.connectify.utils;

import com.connectify.controller.ChatCardController;
import com.connectify.model.entities.Message;
import javafx.application.Platform;

public class ChatCardHandler {
    public static void updateChatCard(Message message) {
        Platform.runLater(()->{
            ChatManager chatManager = ChatManagerFactory.getChatManager(message.getChatId());
            ChatCardController chatCardController= chatManager.getChatCardController();
            chatCardController.updateUnreadMessagesNumber();
            chatCardController.setLastMessage(message.getContent());
            chatCardController.setTimestamp(message.getTimestamp());
        });
    }
}
