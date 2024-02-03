package com.connectify.services;

import com.connectify.dto.MessageDTO;
import com.connectify.dto.MessageSentDTO;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.dao.MessageDAO;
import com.connectify.model.dao.impl.MessageDAOImpl;
import com.connectify.model.entities.Message;

public class MessageService {
    public MessageDTO storeMessage(MessageSentDTO message){
        MessageDAO messageDAO = new MessageDAOImpl();
        MessageMapper mapper = MessageMapper.INSTANCE;
        Message storedMessage = messageDAO.insertSentMessage(mapper.messageSentDtoTOMessage(message));
        return mapper.messageToMessageDto(storedMessage);
    }
}
