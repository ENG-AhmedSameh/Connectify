package com.connectify.controller;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
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
}
