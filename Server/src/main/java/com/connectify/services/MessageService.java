package com.connectify.services;

import com.connectify.dto.MessageDTO;
import com.connectify.mapper.MessageMapper;
import com.connectify.model.dao.ChatMembersDAO;
import com.connectify.model.dao.MessageDAO;
import com.connectify.model.dao.impl.ChatMembersDAOImpl;
import com.connectify.model.dao.impl.MessageDAOImpl;
import com.connectify.model.entities.ChatMember;

import java.util.List;

public class MessageService {
    public void storeMessage(MessageDTO message){
        MessageDAO messageDAO = new MessageDAOImpl();
        MessageMapper mapper = MessageMapper.INSTANCE;
        messageDAO.insert(mapper.messageDtoTOMessage(message));
    }
}
