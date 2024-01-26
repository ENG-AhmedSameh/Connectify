package com.connectify.services;

import com.connectify.dto.SignUpRequest;
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
}
