package com.connectify.mapper;

import com.connectify.dto.ChatMemberDTO;
import com.connectify.model.entities.ChatMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChatMemberMapper {

    ChatMemberMapper INSTANCE = Mappers.getMapper(ChatMemberMapper.class);

    ChatMember chatMemberDtoToChatMember(ChatMemberDTO chatMemberDTO);
    ChatMemberDTO chatMemberToChatMemberDto(ChatMember chatMember);
    List<ChatMember> chatMemberDtoListToChatMemberList(List<ChatMemberDTO> chatMemberDTOList);
    List<ChatMemberDTO> chatMemberListToChatMemberDtoList(List<ChatMember> chatMemberList);
}
