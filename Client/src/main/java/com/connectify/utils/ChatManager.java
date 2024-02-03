package com.connectify.utils;

import com.connectify.controller.ChatCardController;
import com.connectify.controller.ChatController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ChatManager {
    private int chatID;
    private AnchorPane chatCardPane;
    private BorderPane chatPane;
    private ChatCardController chatCardController;
    private ChatController chatController;

    public ChatManager(int chatID){
        this.chatID=chatID;
    }
    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public AnchorPane getChatCardPane() {
        return chatCardPane;
    }

    public void setChatCardPane(AnchorPane chatCardPane) {
        this.chatCardPane = chatCardPane;
    }

    public BorderPane getChatPane() {
        return chatPane;
    }

    public void setChatPane(BorderPane chatPane) {
        this.chatPane = chatPane;
    }

    public ChatCardController getChatCardController() {
        return chatCardController;
    }

    public void setChatCardController(ChatCardController chatCardController) {
        this.chatCardController = chatCardController;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

}

