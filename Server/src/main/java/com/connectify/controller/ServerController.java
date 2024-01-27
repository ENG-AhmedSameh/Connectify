package com.connectify.controller;

import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.SignUpRequest;
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
}
