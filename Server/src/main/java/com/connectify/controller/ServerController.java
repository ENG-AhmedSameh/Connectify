package com.connectify.controller;


import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.*;
import com.connectify.services.ChatService;
import com.connectify.services.MessageService;
import com.connectify.services.UserChatsService;
import com.connectify.dto.ContactsDTO;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.services.ContactService;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    UserService userService;
    MessageService messageService;

    ChatService chatService;
    ContactService contactService;
    public ServerController() throws RemoteException {
        userService = new UserService();
        messageService = new MessageService();
        chatService = new ChatService();
        contactService =new ContactService();
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
    public void prepareCurrentChat(ChatMemberDTO chatMemberDTO) throws RemoteException {
        chatService.prepareCurrentChat(chatMemberDTO);
    }

    @Override
    public List<ContactsDTO> getContacts(String phoneNumber) throws RemoteException {
        return contactService.getContactsDTOList(phoneNumber);
    }

}
