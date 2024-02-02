package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.loaders.ChatCardLoader;
import com.connectify.loaders.ViewLoader;
import com.connectify.mapper.ChatMemberMapper;
import com.connectify.model.entities.ChatMember;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class AllChatsPaneController implements Initializable {

    ViewLoader loader = ViewLoader.getInstance();

    @FXML
    private TextField chatSearchTextField;

    @FXML
    private AnchorPane allChatsAnchorPane;

    @FXML
    private ScrollPane allChatsScrollPane;

    @FXML
    private VBox allChatsVBox;

    private ServerAPI server;
    private static String currentUserId;
    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentId) {
        currentUserId = currentId;
    }

    public AllChatsPaneController(){
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
        loadAllUserChats();
        System.out.println("done");
    }

    public void addChatOnChatPane(int chatId, int unread, String name, byte[] picture, String lastMessage, Timestamp timestamp){
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane(chatId, unread,name,picture,lastMessage,timestamp);
        allChatsVBox.getChildren().add(chatCard);
    }
    private void loadAllUserChats(){
        try {
            allChatsVBox.getChildren().removeAll();
            List<ChatCardsInfoDTO> chatCardsInfoDTOS = server.getUserChatsCardsInfo(Client.getConnectedUser().getPhoneNumber());
            for(ChatCardsInfoDTO chat:chatCardsInfoDTOS)
                addChatOnChatPane(chat.getChatID(),chat.getUnreadMessagesNumber(),chat.getName(),chat.getPicture(),chat.getLastMessage(),chat.getTimestamp());
            chatCardsInfoDTOS.stream().forEach(System.out::println);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
