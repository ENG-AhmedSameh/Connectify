package com.connectify.utils;

import com.connectify.Client;
import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.controller.AllChatsPaneController;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.MessageDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.entities.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.Notifications;
import com.connectify.controller.IncomingFriendRequestController;
import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.loaders.IncomingFriendRequestCardLoader;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CurrentUser extends UnicastRemoteObject implements ConnectedUser, Serializable {

    private final String phoneNumber;

    private static final Map<Integer, ObservableList<Message>> chatListMessagesMap = new HashMap<>();

    public CurrentUser(String phoneNumber) throws RemoteException {
        super();
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void receiveNotification(String title, String message) throws RemoteException {
        Platform.runLater(() -> {
            Notifications.create()
                    .title(title)
                    .text(message)
                    .darkStyle()
                    .threshold(3, Notifications.create().title("Collapsed Notification"))
                    .showInformation();
        });
    }

    public String getPhoneNumber() throws RemoteException {
        return phoneNumber;
    }

    @Override
    public void receiveMessage(MessageDTO messageDTO) throws RemoteException {
        System.out.println(messageDTO.getContent());
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message receivedMessage = mapper.messageDtoToMessage(messageDTO);
        ChatCardHandler.updateChatCard(receivedMessage);
        int chatID = messageDTO.getChatId();
        chatListMessagesMap.putIfAbsent(chatID, FXCollections.observableArrayList());
        chatListMessagesMap.get(chatID).add(receivedMessage);
    }



    public static ObservableList<Message> getMessageList(int chatID) {
        chatListMessagesMap.putIfAbsent(chatID, FXCollections.observableArrayList());
        return chatListMessagesMap.get(chatID);
    }

    @Override
    public void forceLogout() throws RemoteException {
        StageManager.getInstance().resetHomeScene();
        StageManager.getInstance().switchToLogin();
    }

    @Override
    public void makeNewChatCard(ChatCardsInfoDTO chat) throws RemoteException{
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane(chat.getChatID(),chat.getUnreadMessagesNumber(),chat.getName(),chat.getPicture(),chat.getLastMessage(),chat.getTimestamp());
        AllChatsPaneController.getChatsPanesList().add(chatCard);
    }


}
