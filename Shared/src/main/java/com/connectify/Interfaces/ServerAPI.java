package com.connectify.Interfaces;


import com.connectify.dto.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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

    List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String phoneNumber) throws RemoteException;

    boolean acceptFriendRequest(int invitationId) throws RemoteException;
}
