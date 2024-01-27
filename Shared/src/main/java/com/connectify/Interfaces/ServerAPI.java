package com.connectify.Interfaces;


import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAPI extends Remote{

    boolean signUp(SignUpRequest signUpRequest) throws RemoteException;

    LoginResponse login(LoginRequest loginRequest) throws RemoteException;

}
