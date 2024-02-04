package com.connectify.controller;


import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.*;
import com.connectify.services.*;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    private final UserService userService;
    private final MessageService messageService;

    private final ChatService chatService;

    private final AttachmentService attachmentService;
    public ServerController() throws RemoteException {
        userService = new UserService();
        messageService = new MessageService();
        chatService = new ChatService();
        attachmentService = new AttachmentService();
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

    @Override
    public List<ChatMemberDTO> getAllUserChats(String userId) throws RemoteException {
        var service = new UserChatsService();
        return service.getAllChats(userId);
    }

    @Override
    public List<ChatCardsInfoDTO> getUserChatsCardsInfo(String userId) throws RemoteException{
        var service = new UserChatsService();
        return service.getAllChatsInfo(userId);
    }

    @Override
    public void changeProfileAndBio(ImageBioChangeRequest request) throws RemoteException {
        userService.changeProfileAndBio(request);
    }

    @Override
    public void sendMessage(MessageSentDTO message) throws RemoteException {
        MessageDTO storedMessage = messageService.storeMessage(message);
        chatService.sendMessage(storedMessage);
    }

    @Override
    public void sendAttachment(MessageSentDTO message) throws RemoteException{
        Integer attachmentId = attachmentService.storeAttachment(message);
        message.setAttachmentId(attachmentId);
        MessageDTO storedMessage = messageService.storeMessage(message);
        chatService.sendMessage(storedMessage);
    }

    public File getAttachment(Integer attachmentId) throws RemoteException{
        return attachmentService.getAttachment(attachmentId);
    }

    @Override
    public void prepareCurrentChat(ChatMemberDTO chatMemberDTO) throws RemoteException {
        chatService.prepareCurrentChat(chatMemberDTO);
    }
}
