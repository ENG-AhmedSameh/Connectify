package com.connectify.utils;

import com.connectify.Client;
import com.connectify.controller.ChatCardController;
import com.connectify.controller.ChatController;
import com.connectify.dto.MemberInfoDTO;
import com.connectify.mapper.MemberInfoMapper;
import com.connectify.model.entities.User;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

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

    private ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
    private final Boolean privateChat;
    private List<User> chatMembers;
    private Map<String,User> membersInfoMap;
    private String groupLastSender = "";

    public ChatManager(int chatID){
        this.chatID=chatID;
        privateChat = RemoteManager.getInstance().isPrivateChat(chatID);
        List<MemberInfoDTO> memberInfoDTOS = null;
        try {
            memberInfoDTOS = RemoteManager.getInstance().getAllChatOtherMembersInfo(chatID, CurrentUser.getInstance().getPhoneNumber());
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        }
        chatMembers = MemberInfoMapper.Instance.memberInfoDtoListToUserList(memberInfoDTOS);
        membersInfoMap = new HashMap<>();
        for(User user:chatMembers) {
            membersInfoMap.putIfAbsent(user.getPhoneNumber(), user);
        }
        if(chatMembers.size()==1){
            colorProperty.set(getInitialModeAndStatusColor(chatMembers.get(0).getPhoneNumber()));
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

    public void clearMembersInfoMap(){
        chatMembers.clear();
        membersInfoMap.clear();
    }

    public String getChatContact(int chatID) {
        if(chatMembers.size()==1)
            return chatMembers.get(0).getPhoneNumber();
        else
            return "";
    }

    public Color getColorPropertyValue() {
        return colorProperty.get();
    }

    public ObjectProperty<Color> getcolorProperty() {
        return colorProperty;
    }
    public void changeUserModeColorPropertyToOffline(){
        colorProperty.setValue(Color.GRAY);
        System.out.println(colorProperty.get());
    }
    public Color getInitialModeAndStatusColor(String phoneNumber){
        if(RemoteManager.getInstance().getContactMode(phoneNumber)== Mode.OFFLINE)
            return Color.GRAY;
        else{
            switch (RemoteManager.getInstance().getContactStatus(phoneNumber)){
                case Status.AVAILABLE -> {
                    return Color.GREEN;
                }
                case Status.BUSY -> {
                    return Color.RED;
                }
                case Status.AWAY -> {
                    return Color.ORANGE;
                }
            }
            return Color.GREEN;
        }
    }

    public void changeUserModeColorProperty(Status status) {
        switch (status){
            case Status.AVAILABLE -> {
                colorProperty.set(Color.GREEN);
            }
            case Status.BUSY -> {
                colorProperty.set(Color.RED);
            }
            case Status.AWAY -> {
                colorProperty.set(Color.ORANGE);
            }
        }
    }
}

