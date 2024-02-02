package com.connectify.Interfaces;


import com.connectify.dto.FriendToAddResponse;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAPI extends Remote{

    boolean signUp(SignUpRequest signUpRequest) throws RemoteException;
    boolean updateUserProfile(UpdateUserInfoRequest updateUserInfoRequest) throws RemoteException;
    boolean updateUserPicture(String phoneNumber, byte[] picture) throws RemoteException;
    boolean updateUserPassword(String phoneNumber,byte[] salt, String password) throws RemoteException;
    UserProfileResponse getUserProfile(String phoneNumber) throws RemoteException;

    LoginResponse login(LoginRequest loginRequest) throws RemoteException;

    FriendToAddResponse getFriendToAdd(String phoneNumber) throws RemoteException;

    boolean sendInvitation(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException;

    boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException;

    boolean logout(String phoneNumber) throws RemoteException;

    void registerConnectedUser(ConnectedUser user) throws RemoteException;
    void unregisterConnectedUser(ConnectedUser user) throws RemoteException;

}
