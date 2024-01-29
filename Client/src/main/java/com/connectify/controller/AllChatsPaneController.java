package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
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

    public AllChatsPaneController(String currentId){
        currentUserId = currentId;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        for(int i = 0;i<10;i++)
//            addChatOnChatPane();
        try {
            server = (ServerAPI) Client.registry.lookup("server");
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
        loadAllUserChats(currentUserId);
        System.out.println("done");
    }

    public void addChatOnChatPane(){
        AnchorPane chatCard = ChatCardLoader.loadChatCardAnchorPane();
        allChatsVBox.getChildren().add(chatCard);
    }
    private void loadAllUserChats(String userID){
        try {
            List<ChatMemberDTO> userChatsDtoList = server.getAllUserChats(userID);
            ChatMemberMapper mapper = ChatMemberMapper.INSTANCE;
            List<ChatMember> userChatsList =mapper.chatMemberDtoListToChatMemberList(userChatsDtoList);
            userChatsList.stream().forEach(System.out::println);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
