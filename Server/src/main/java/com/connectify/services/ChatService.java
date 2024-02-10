package com.connectify.services;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.app.Server;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.dto.MemberInfoDTO;
import com.connectify.dto.MessageDTO;
import com.connectify.mapper.ChatMemberMapper;
import com.connectify.mapper.MemberInfoMapper;
import com.connectify.model.dao.ChatDAO;
import com.connectify.model.dao.ChatMembersDAO;
import com.connectify.model.dao.impl.ChatDAOImpl;
import com.connectify.model.dao.impl.ChatMembersDAOImpl;
import com.connectify.model.entities.ChatMember;
import com.connectify.model.entities.User;

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

    public void prepareCurrentChat(ChatMemberDTO chatMemberDTO) {
        ChatMemberMapper mapper = ChatMemberMapper.INSTANCE;
        ChatMember chatMember = mapper.chatMemberDtoToChatMember(chatMemberDTO);
        ChatMembersDAO chatMembersDAO = new ChatMembersDAOImpl();
        chatMembersDAO.prepareCurrentChat(chatMember);
    }

    public boolean isPrivateChat(int chatID) {
        ChatDAO chatDAO = new ChatDAOImpl();
        return chatDAO.isPrivateChat(chatID);
    }

    public List<MemberInfoDTO> getAllOtherMembersInfo(int chatId, String member) {
        ChatMembersDAO chatMembersDAO = new ChatMembersDAOImpl();
        List<User> usersInfo = chatMembersDAO.getAllOtherChatMembersInfo(chatId,member);
        MemberInfoMapper mapper = MemberInfoMapper.Instance;
        return mapper.usersListToMemberInfoDto(usersInfo);
    }
}
