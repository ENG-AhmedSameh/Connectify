package com.connectify.utils;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.controller.ChatCardController;
import com.connectify.controller.ChatController;
import com.connectify.model.entities.User;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ChatManager {
    private int chatID;
    private AnchorPane chatCardPane;
    private BorderPane chatPane;
    private ChatCardController chatCardController;
    private ChatController chatController;

    private Boolean isGroupChat;

    List<User> chatMembers;

    ServerAPI server;

    public ChatManager(int chatID){
        this.chatID=chatID;
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
            //isGroupChat = server.getChatType(String chatID);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
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

