package com.connectify.controller;


import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.*;
import com.connectify.services.InvitationService;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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

    @Override
    public boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException {
        return invitationService.isInvitationSent(senderPhoneNumber, receiverPhoneNumber);
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
    public List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String phoneNumber) throws RemoteException {
        return invitationService.getIncomingFriendRequests(phoneNumber);
    }

    @Override
    public boolean acceptFriendRequest(int invitationId) throws RemoteException {
        return invitationService.acceptFriendRequest(invitationId);
    }

    @Override
    public boolean cancelFriendRequest(int invitationId) throws RemoteException {
        return invitationService.cancelFriendRequest(invitationId);
    }

    @Override
    public boolean updateUserProfile(UpdateUserInfoRequest updateUserInfoRequest) throws RemoteException {
        return userService.updateUser(updateUserInfoRequest);
    }

    @Override
    public boolean updateUserPicture(String phoneNumber, byte[] picture) {
        return userService.updatePicture(phoneNumber, picture);
    }

    @Override
    public boolean updateUserPassword(String phoneNumber, byte[] salt, String password) {
        return userService.updatePassword(phoneNumber, salt, password);
    }

    @Override
    public UserProfileResponse getUserProfile(String phoneNumber) {
        return  userService.getUserProfile(phoneNumber);
    }
}
