package com.connectify.services;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Server;
import com.connectify.dto.ImageBioChangeRequest;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.dto.*;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.UserDAO;
import com.connectify.model.dao.impl.UserDAOImpl;
import com.connectify.model.entities.User;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;
import com.connectify.util.PasswordManager;

import java.rmi.RemoteException;


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
            return new LoginResponse(true, "Login successful");
        }
        return new LoginResponse(false, "Password is not correct");
    }

    public UserProfileResponse getUserProfile(String phoneNumber) {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.get(phoneNumber);
        return UserMapper.INSTANCE.userToUserProfileResponse(user);
    }

    public boolean updateUser(UpdateUserInfoRequest request) {
        User user = UserMapper.INSTANCE.updateUserInfoRequestToUser(request);
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.update(user);
    }

    public boolean updatePassword(String phoneNumber, byte[] salt, String password) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updatePassword(phoneNumber, salt, password);
    }

    public boolean updatePicture(String phoneNumber, byte[] picture) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updatePicture(phoneNumber, picture);
    }

    public boolean logoutUser(String phoneNumber){
        Server.getConnectedUsers().remove(phoneNumber);
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updateMode(phoneNumber, Mode.OFFLINE);
    }

    public void registerConnectedUser(ConnectedUser user) {
        try {
            UserDAO userDAO = new UserDAOImpl();
            userDAO.updateMode(user.getPhoneNumber(), Mode.ONLINE);
            Server.getConnectedUsers().put(user.getPhoneNumber(), user);
            System.out.println("Registered user: " + user.getPhoneNumber());
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    public boolean changeProfileAndBio(ImageBioChangeRequest request) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.updateImage(request.getPhoneNumber(), request.getImage()) && userDAO.updateBio(request.getPhoneNumber(), request.getBio());
    }

    public FriendToAddResponse getFriendToAddData(String phone) {
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.get(phone);
        return UserMapper.INSTANCE.userToFriendToAddResponse(user);
    }

    public User getUserInfo(String phoneNumber) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.get(phoneNumber);
    }
}
