package com.connectify.services;

import com.connectify.dto.MessageDTO;
import com.connectify.dto.MessageSentDTO;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.dao.MessageDAO;
import com.connectify.model.dao.impl.MessageDAOImpl;
import com.connectify.model.entities.Message;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO = new MessageDAOImpl();
    public MessageDTO storeMessage(MessageSentDTO message){
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message storedMessage = messageDAO.insertSentMessage(mapper.messageSentDtoTOMessage(message));
        return mapper.messageToMessageDto(storedMessage);
    }

    public List<MessageDTO> getAllChatMessages(int chatID, Integer idLimit) {
        if(idLimit==null)
            return MessageMapper.INSTANCE.messageListToMessageDtoList(messageDAO.getAllChatMessages(chatID));
        return MessageMapper.INSTANCE.messageListToMessageDtoList(messageDAO.getAllChatMessagesUntilLimit(chatID,idLimit));
    }
}
