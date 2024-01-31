package com.connectify.controller;

import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.SignUpRequest;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerController extends UnicastRemoteObject implements ServerAPI {
    public ServerController() throws RemoteException {
    }

    @Override
    public boolean signUp(SignUpRequest signUpRequest) throws RemoteException {
        var service = new UserService();
        return service.insertUser(signUpRequest);
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
