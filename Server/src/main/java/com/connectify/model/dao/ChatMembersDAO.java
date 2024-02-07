package com.connectify.model.dao;

import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.MemberInfoDTO;
import com.connectify.mapper.MemberInfoMapper;
import com.connectify.model.entities.ChatMember;
import com.connectify.model.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface ChatMembersDAO extends DAO<ChatMember, ChatMember> {
    List<ChatMember> getAllUserChats(String userID) throws SQLException;

    List<ChatCardsInfoDTO> getAllUserChatsInfo(String userId) throws SQLException;

    List<ChatMember> getAllChatMembers(int chatID) throws SQLException;

    List<ChatMember> getAllOtherChatMembers(int chatId, String sender);

    void prepareCurrentChat(ChatMember chatMember);

    List<User> getAllOtherChatMembersInfo(int chatID, String member);

    ChatCardsInfoDTO getUserLastChatInfo(String userId);

    boolean closeAllUserChats(String phoneNumber);

    boolean closeAllUsersOpenedChats();
}
