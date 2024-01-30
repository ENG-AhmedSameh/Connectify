package com.connectify.services;

import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.UserDAO;
import com.connectify.model.dao.impl.UserDAOImpl;
import com.connectify.model.entities.User;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;
import com.connectify.util.PasswordManager;

public class UserService {

    public boolean insertUser(SignUpRequest request){
        User user = UserMapper.INSTANCE.signUpRequestToUser(request);
        user.setMode(Mode.ONLINE);
        user.setStatus(Status.AVAILABLE);
        UserDAO dao = new UserDAOImpl();
        return dao.insert(user);
    }

    public LoginResponse loginUser(LoginRequest request){
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.get(request.getPhoneNumber());
        if(user == null){
            return new LoginResponse(false, "Phone number is not correct or registered");
        }
        String hashedPassword = user.getPassword();
        boolean isCorrect = PasswordManager.isEqual(hashedPassword, request.getPassword(), user.getSalt());
        if(isCorrect){
            userDAO.updateMode(user.getPhoneNumber(), Mode.ONLINE);
            return new LoginResponse(true, "Login successful");
        }
        return new LoginResponse(false, "Password is not correct");
    }

    public boolean logoutUser(String phoneNumber){
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updateMode(phoneNumber, Mode.OFFLINE);
    }
}
