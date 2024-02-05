package com.connectify.services;

import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.mapper.ChatMemberMapper;
import com.connectify.model.dao.ChatMembersDAO;
import com.connectify.model.dao.impl.ChatMembersDAOImpl;
import com.connectify.model.entities.ChatMember;

import java.sql.SQLException;
import java.util.List;

public class UserChatsService {
    public List<ChatMemberDTO> getAllChats(String userId) {

        ChatMembersDAO dao = new ChatMembersDAOImpl();
        try {
            List<ChatMember> userChats = dao.getAllUserChats(userId);
            ChatMemberMapper mapper = ChatMemberMapper.INSTANCE;
            return mapper.chatMemberListToChatMemberDtoList(userChats);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ChatCardsInfoDTO> getAllChatsInfo(String userId){
        ChatMembersDAO dao = new ChatMembersDAOImpl();
        try {
            return dao.getAllUserChatsInfo(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ChatCardsInfoDTO getLastChatInfo(String userId){
        ChatMembersDAO dao = new ChatMembersDAOImpl();
        return dao.getUserLastChatInfo(userId);
    }
}
