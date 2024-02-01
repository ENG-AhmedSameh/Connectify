package com.connectify.controller;


import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    UserService userService;
    public ServerController() throws RemoteException {
        userService = new UserService();
    }

    @Override
    public boolean signUp(SignUpRequest signUpRequest) throws RemoteException {
        return userService.insertUser(signUpRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) throws RemoteException {
        return userService.loginUser(loginRequest);
    }

    @Override
    public boolean logout(String phoneNumber) throws RemoteException {
        return userService.logoutUser(phoneNumber);
    }

    @Override
    public void registerConnectedUser(ConnectedUser user) throws RemoteException {
        userService.registerConnectedUser(user);
    }

    @Override
    public void unregisterConnectedUser(ConnectedUser user) throws RemoteException {
        userService.unregisterConnectedUser(user);
    }

    @Override
    public boolean updateUserProfile(UpdateUserInfoRequest updateUserInfoRequest) throws RemoteException {
        var userService = new UserService();
        return userService.updateUser(updateUserInfoRequest);
    }

    @Override
    public boolean updateUserPicture(String phoneNumber, byte[] picture) {
        var userService = new UserService();
        return userService.updatePicture(phoneNumber, picture);
    }

    @Override
    public boolean updateUserPassword(String phoneNumber, String password) {
        var userService = new UserService();
        return userService.updatePassword(password, password);
    }

    @Override
    public UserProfileResponse getUserProfile(String phoneNumber) {
        var userService = new UserService();
        return  userService.getUserProfile(phoneNumber);
    }
}
