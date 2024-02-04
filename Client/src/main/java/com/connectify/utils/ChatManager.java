package com.connectify.utils;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.controller.ChatCardController;
import com.connectify.controller.ChatController;
import com.connectify.dto.MemberInfoDTO;
import com.connectify.mapper.MemberInfoMapper;
import com.connectify.model.entities.User;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private int chatID;
    private AnchorPane chatCardPane;
    private BorderPane chatPane;
    private ChatCardController chatCardController;
    private ChatController chatController;

    private final Boolean privateChat;

    private List<User> chatMembers;
    private Map<String,User> membersInfoMap;
    private String groupLastSender = "";

    ServerAPI server;

    public ChatManager(int chatID){
        this.chatID=chatID;
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
            privateChat = server.isPrivateChat(chatID);
            List<MemberInfoDTO> memberInfoDTOS = server.getAllChatOtherMembersInfo(chatID,Client.getConnectedUser().getPhoneNumber());
            chatMembers = MemberInfoMapper.Instance.memberInfoDtoListToUserList(memberInfoDTOS);
            membersInfoMap = new HashMap<>();
            for(User user:chatMembers){
                membersInfoMap.putIfAbsent(user.getPhoneNumber(),user);
            }
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

    public Boolean isPrivateChat() {
        return privateChat;
    }
    public User getUserInfo(String member){
        if(membersInfoMap.containsKey(member)){
            return membersInfoMap.get(member);
        }
        return null;
    }

    public String getGroupLastSender() {
        return groupLastSender;
    }

    public void setGroupLastSender(String groupLastSender) {
        this.groupLastSender = groupLastSender;
    }
}

