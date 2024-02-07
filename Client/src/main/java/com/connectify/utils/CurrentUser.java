package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.controller.AllChatsPaneController;
import com.connectify.controller.ChatController;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.MessageDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.entities.Message;
import com.connectify.model.enums.Status;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;
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

    private static Map<Integer,Integer> chatFirstReceivedMessageIdMap = new HashMap<>();

    private static AllChatsPaneController allChatsController;
    private static ChatManagerFactory chatManagerFactory = new ChatManagerFactory();
    private static ChatPaneFactory chatPaneFactory = new ChatPaneFactory();

    private static final Map<Integer, ObservableList<Message>> chatListMessagesMap = new HashMap<>();

    private CurrentUser() throws RemoteException {
        super();
        String token = PropertiesManager.getInstance().getToken();
        if(token != null && !token.isBlank()){
            this.phoneNumber = RemoteManager.getInstance().getPhoneNumberByToken(token);
        }
    }
    public static CurrentUser getInstance() throws RemoteException {
        if (instance == null) {
            instance = new CurrentUser();
            if(instance.getPhoneNumber() != null){
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        RemoteManager.getInstance().logout(CurrentUser.getInstance());
                    } catch (RemoteException e) {
                        System.err.println("Remote Exception: " + e.getMessage());
                    }
                }));
            }
        }
        return instance;
    }

    public static void reset(){
        instance = null;
    }

    @Override
    public void receiveNotification(String title, String message) throws RemoteException {
        Platform.runLater(() -> {
            Image icon = new Image(getClass().getResource("/images/notification.png").toString());
            Notifications.create()
                    .title(title)
                    .text(message)
                    .graphic(new ImageView(icon))
                    .threshold(3, Notifications.create().title("Collapsed Notification"))
                    .show();
        });
    }

    public String getPhoneNumber() throws RemoteException {
        return phoneNumber;
    }

    @Override
    public void receiveMessage(MessageDTO messageDTO) throws RemoteException {
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message receivedMessage = mapper.messageDtoToMessage(messageDTO);
        ChatCardHandler.updateChatCard(receivedMessage);
        int chatID = messageDTO.getChatId();
        chatListMessagesMap.putIfAbsent(chatID, FXCollections.observableArrayList());
        chatListMessagesMap.get(chatID).add(receivedMessage);
        chatFirstReceivedMessageIdMap.putIfAbsent(chatID,receivedMessage.getMessageId());
        if(ChatBot.isEnabled()){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(()->{
                String chatBotMessage = ChatBot.call(receivedMessage.getContent());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                ChatController controller = chatManagerFactory.getChatManager(chatID).getChatController();
                Platform.runLater(()->controller.sendChatBotMessage(chatBotMessage));
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
        CurrentUser.reset();
        Platform.runLater(() ->{
            CurrentUser.getAllChatsController().clearChatsCardList();
            CurrentUser.getChatManagerFactory().clearChatManagersMap();
            CurrentUser.getChatPaneFactory().clearChats();
            StageManager.getInstance().resetHomeScene();
            StageManager.getInstance().switchToLogin();
        });
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
}
