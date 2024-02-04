package com.connectify.mapper;


import com.connectify.dto.MemberInfoDTO;
import com.connectify.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberInfoMapper {
    MemberInfoMapper Instance = Mappers.getMapper(MemberInfoMapper.class);

    List<User> memberInfoDtoListToUserList(List<MemberInfoDTO> memberInfoDTOS);
    List<MemberInfoDTO> usersListToMemberInfoDto(List<User> users);
}
