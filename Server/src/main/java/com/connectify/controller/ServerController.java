package com.connectify.controller;

import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.ChatMemberDTO;
import com.connectify.dto.SignUpRequest;
import com.connectify.model.entities.ChatMember;
import com.connectify.services.UserChatsService;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerController extends UnicastRemoteObject implements ServerAPI {
    public ServerController() throws RemoteException {
    }

    @Override
    public boolean signUp(SignUpRequest signUpRequest) throws RemoteException {
        var service = new UserService();
        return service.insertUser(signUpRequest);
    }

    @Override
    public List<ChatMemberDTO> getAllUserChats(String userId) throws RemoteException {
        var service = new UserChatsService();
        return service.getAllChats(userId);
    }
}
