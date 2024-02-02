package com.connectify.services;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Server;
import com.connectify.dto.MessageDTO;
import com.connectify.model.dao.ChatMembersDAO;
import com.connectify.model.dao.impl.ChatMembersDAOImpl;
import com.connectify.model.entities.ChatMember;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ChatService {

    private List<ChatMember> getChatMembers(MessageDTO messageDTO){
        ChatMembersDAO chatMembersDAO = new ChatMembersDAOImpl();
        return chatMembersDAO.getAllOtherChatMembers(messageDTO.getChatId(), messageDTO.getSender());
    }
    public void sendMessage(MessageDTO messageDTO){
        List<ChatMember> chatMembers = getChatMembers(messageDTO);
        Map<String, ConnectedUser> usersMap = Server.getConnectedUsers();
        for(ChatMember chatMember:chatMembers){
            ConnectedUser user = usersMap.get(chatMember.getMember());
            if(user!=null) {
                try {
                    user.receiveMessage(messageDTO);
                } catch (RemoteException e) {
                    System.err.println("Can't receive message, details: "+e.getMessage());
                }
            }
        }
    }

}
