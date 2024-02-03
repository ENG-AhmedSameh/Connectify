package com.connectify.mapper;

import com.connectify.dto.*;
import com.connectify.model.entities.User;
import com.connectify.dto.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "salt", target = "salt")
    User signUpRequestToUser(SignUpRequest signUpRequest);

    FriendToAddResponse userToFriendToAddResponse(User user);


    User updateUserInfoRequestToUser(UpdateUserInfoRequest updateUserInfoRequest);

    UserProfileResponse userToUserProfileResponse(User user);
}
