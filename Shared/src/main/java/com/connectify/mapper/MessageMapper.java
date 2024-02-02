package com.connectify.mapper;

import com.connectify.dto.MessageDTO;
import com.connectify.model.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    Message messageDtoTOMessage(MessageDTO messageDTO);
}
