package com.connectify.mapper;

import com.connectify.dto.MessageDTO;
import com.connectify.dto.MessageSentDTO;
import com.connectify.model.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    Message messageSentDtoTOMessage(MessageSentDTO messageSentDTO);
    MessageSentDTO messageToMessageSentDto(Message message);

    Message messageDtoToMessage(MessageDTO messageDTO);
    MessageDTO messageToMessageDto(Message message);

    List<MessageDTO> messageListToMessageDtoList(List<Message> allChatMessages);

    List<Message> messageDtoListToMessageList(List<MessageDTO> historyMeessageDtoList);
}
