package com.connectify.model.dao;


import com.connectify.model.entities.Message;

import java.util.List;

public interface MessageDAO extends DAO<Message,Integer>{
    Message insertSentMessage(Message m);

    List<Message> getAllChatMessagesUntilLimit(int chatID, Integer idLimit);

    List<Message> getAllChatMessages(int chatID);
}
