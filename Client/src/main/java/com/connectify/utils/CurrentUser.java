package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.controller.AllChatsPaneController;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.MessageDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.mapper.*;
import com.connectify.model.entities.Message;
import com.connectify.model.enums.Status;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrentUser extends UnicastRemoteObject implements ConnectedUser, Serializable {
    private static CurrentUser instance;

    private String phoneNumber;

    private static Map<Integer,Integer> chatFirstReceivedMessageIdMap;

    private static AllChatsPaneController allChatsController;
    private static ChatManagerFactory chatManagerFactory;
    private static ChatPaneFactory chatPaneFactory;

    private static Map<Integer, ObservableList<Message>> chatListMessagesMap ;

    private CurrentUser() throws RemoteException {
        super();
        String token = PropertiesManager.getInstance().getToken();
        if(token != null && !token.isBlank()){
            this.phoneNumber = RemoteManager.getInstance().getPhoneNumberByToken(token);
        }
        chatManagerFactory = new ChatManagerFactory();
        chatPaneFactory = new ChatPaneFactory();
        chatListMessagesMap = new HashMap<>();
        chatFirstReceivedMessageIdMap = new HashMap<>();
    }
    public static CurrentUser getInstance() throws RemoteException {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }


    @Override
    public void receiveNotification(String title, String message) throws RemoteException {
        NotificationsManager.showServerNotification(title, message);
    }

    public String getPhoneNumber() throws RemoteException {
        return phoneNumber;
    }

    @Override
    public void receiveMessage(MessageDTO messageDTO) throws RemoteException {
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message receivedMessage = mapper.messageDtoToMessage(messageDTO);
        if(receivedMessage.getChatId()!=chatManagerFactory.getActiveChatID()){
            String notificationMessage = messageDTO.getContent();
            if(notificationMessage.length()>40)
                notificationMessage = messageDTO.getContent().substring(0,40)+"...";
            NotificationsManager.showMessageNotification("You have a new message",
                    chatManagerFactory.getChatManager(messageDTO.getChatId()).
                            getChatCardController().getChatName()+": "+notificationMessage);
        }

        ChatCardHandler.updateChatCard(receivedMessage);
        int chatID = messageDTO.getChatId();
        chatListMessagesMap.putIfAbsent(chatID, FXCollections.observableArrayList());
        chatListMessagesMap.get(chatID).add(receivedMessage);
        chatFirstReceivedMessageIdMap.putIfAbsent(chatID,receivedMessage.getMessageId());
        if(ChatBot.isEnabled()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    ChatBot.replyToMessage(receivedMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static ObservableList<Message> getMessageList(int chatID) {
        chatListMessagesMap.putIfAbsent(chatID, FXCollections.observableArrayList());
        return chatListMessagesMap.get(chatID);
    }

    @Override
    public void forceLogout() throws RemoteException {
        RemoteManager.reset();
        Platform.runLater(() ->{
            CurrentUser.resetAllData();
            CurrentUser.getAllChatsController().clearChatsCardList();
            CurrentUser.getChatManagerFactory().clearChatManagersMap();
            CurrentUser.getChatPaneFactory().clearChats();
            StageManager.getInstance().resetHomeScene();
            StageManager.getInstance().switchToLogin();
        });
    }

    @Override
    public void ping() throws RemoteException {
        RemoteManager.getInstance().sendPingBack(phoneNumber);
    }

    @Override
    public void updateContactModeToOffline(String phoneNumber) throws RemoteException {
        chatManagerFactory.getContactChatManager(phoneNumber).changeUserModeColorPropertyToOffline();
    }

    @Override
    public void updateContactStatus(String phoneNumber, Status status) throws RemoteException {
        chatManagerFactory.getContactChatManager(phoneNumber).changeUserModeColorProperty(status);
    }

    @Override
    public void makeNewChatCard(ChatCardsInfoDTO chat) throws RemoteException{
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane(chat.getChatID(),chat.getUnreadMessagesNumber(),chat.getName(),chat.getPicture(),chat.getLastMessage(),chat.getTimestamp());
        Platform.runLater(()->CurrentUser.getAllChatsController().getChatsPanesList().add(chatCard));
    }

    public static AllChatsPaneController getAllChatsController() {
        return allChatsController;
    }

    public static void setAllChatsController(AllChatsPaneController allChatsController) {
        CurrentUser.allChatsController = allChatsController;
    }

    public static ChatManagerFactory getChatManagerFactory() {
        return chatManagerFactory;
    }

    public static ChatPaneFactory getChatPaneFactory() {
        return chatPaneFactory;
    }

    public static Integer getChatFirstReceivedMessageId(int chatId) {
        return chatFirstReceivedMessageIdMap.get(chatId);
    }

    public static void resetAllData(){
        chatListMessagesMap.clear();
        chatFirstReceivedMessageIdMap.clear();
        allChatsController.clearChatsCardList();
        chatManagerFactory.clearChatManagersMap();
        chatManagerFactory.setActiveChatID(0);
        chatPaneFactory.clearChats();
        chatManagerFactory.clearContactsManagersMap();
        instance = null;
    }
}
