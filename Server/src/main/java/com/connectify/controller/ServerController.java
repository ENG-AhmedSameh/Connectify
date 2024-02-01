package com.connectify.controller;

import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.FriendToAddResponse;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.services.InvitationService;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    UserService userService;
    InvitationService invitationService;
    public ServerController() throws RemoteException {
        userService = new UserService();
        invitationService = new InvitationService();
    }

    @Override
    public boolean signUp(SignUpRequest signUpRequest) throws RemoteException {
        return userService.insertUser(signUpRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) throws RemoteException {
        return userService.loginUser(loginRequest);
    }

    @Override
    public FriendToAddResponse getFriendToAdd(String phoneNumber) throws RemoteException {
        return userService.getFriendToAddData(phoneNumber);
    }

    @Override
    public boolean sendInvitation(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException {
        return invitationService.sendInvitation(senderPhoneNumber, receiverPhoneNumber);
    }
}
