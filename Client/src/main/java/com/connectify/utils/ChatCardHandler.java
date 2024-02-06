package com.connectify.utils;

import com.connectify.controller.ChatCardController;
import com.connectify.model.entities.Message;
import javafx.application.Platform;

public class ChatCardHandler {
    public static void updateChatCard(Message message) {
        Platform.runLater(()->{
            ChatManager chatManager = CurrentUser.getChatManagerFactory().getChatManager(message.getChatId());
            ChatCardController chatCardController= chatManager.getChatCardController();
            chatCardController.updateUnreadMessagesNumber();
            chatCardController.updateCardPosition(chatManager.getChatCardPane());
            chatCardController.setLastMessage(message.getContent());
            chatCardController.setTimestamp(message.getTimestamp());
        });
    }
}
