package com.connectify.mapper;

import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;
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
    User signUpRequestToUser(SignUpRequest signUpRequest);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "status", target = "status")
    User updateUserInfoRequestToUser(UpdateUserInfoRequest updateUserInfoRequest);

    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "picture", target = "picture")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "status", target = "status")
    UserProfileResponse userToUserRequest(User user);
}
