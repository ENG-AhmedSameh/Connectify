package com.connectify.Interfaces;


import com.connectify.dto.*;
import com.connectify.model.entities.Attachments;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerAPI extends Remote{

    boolean signUp(SignUpRequest signUpRequest) throws RemoteException;
    List<ChatMemberDTO> getAllUserChats(String userId) throws RemoteException;

    LoginResponse login(LoginRequest loginRequest) throws RemoteException;

    boolean logout(String phoneNumber) throws RemoteException;

    void registerConnectedUser(ConnectedUser user) throws RemoteException;
    void unregisterConnectedUser(ConnectedUser user) throws RemoteException;

    List<ChatCardsInfoDTO> getUserChatsCardsInfo(String userId) throws RemoteException;
    void changeProfileAndBio(ImageBioChangeRequest request) throws RemoteException;

    void sendMessage(MessageSentDTO message) throws RemoteException;

    void sendAttachment(MessageSentDTO messageSentDTO) throws RemoteException;

    File getAttachment(Integer attachmentId) throws RemoteException;

    void prepareCurrentChat(ChatMemberDTO chatMemberDTO) throws RemoteException;
}
