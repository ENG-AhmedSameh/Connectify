package com.connectify.mapper;

import com.connectify.dto.ContactsDTO;
import com.connectify.dto.UserProfileResponse;
import com.connectify.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

        ContactsDTO userToContactsDTO(User user);
        User contactsDTOToUser(ContactsDTO contactsDTO);
        List<ContactsDTO> userListToContactDTOList(List<User> userList);
        List<User> contactDTOListToUserList(List<ContactsDTO> contactsDtoList);
        ContactsDTO userProfileResponseToContactsDto(UserProfileResponse userProfileResponse);
}


