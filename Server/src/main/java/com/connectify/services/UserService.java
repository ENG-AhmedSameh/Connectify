package com.connectify.services;

import com.connectify.dto.SignUpRequest;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserRequest;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.UserDAO;
import com.connectify.model.dao.impl.UserDAOImpl;
import com.connectify.model.entities.User;

public class UserService {

    public boolean insertUser(SignUpRequest request){
        User user = UserMapper.INSTANCE.signUpRequestToUser(request);
        UserDAO dao = new UserDAOImpl();
        return dao.insert(user);
    }

    public UserRequest getUser(String phoneNumber) {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.get(phoneNumber);
        return UserMapper.INSTANCE.userToUserRequest(user);
    }

    public boolean updateUser(UpdateUserInfoRequest request) {
        User user = UserMapper.INSTANCE.updateUserInfoRequestToUser(request);
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.update(user);
    }

    public boolean updatePassword(String phoneNumber, String password) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updatePassword(phoneNumber, password);
    }

    public boolean updatePicture(String phoneNumber, byte[] picture) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updatePicture(phoneNumber, picture);
    }
}
