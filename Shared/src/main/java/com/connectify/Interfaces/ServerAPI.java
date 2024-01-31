package com.connectify.Interfaces;


import com.connectify.dto.SignUpRequest;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAPI extends Remote{

    boolean signUp(SignUpRequest signUpRequest) throws RemoteException;
    boolean updateUserProfile(UpdateUserInfoRequest updateUserInfoRequest) throws RemoteException;
    boolean updateUserPicture(String phoneNumber, byte[] picture) throws RemoteException;
    boolean updateUserPassword(String phoneNumber, String password) throws RemoteException;
    UserProfileResponse getUserProfile(String phoneNumber) throws RemoteException;

}
