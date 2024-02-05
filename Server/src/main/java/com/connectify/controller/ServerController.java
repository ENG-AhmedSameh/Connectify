package com.connectify.controller;


import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.*;
import com.connectify.model.entities.User;
import com.connectify.services.ChatService;
import com.connectify.services.MessageService;
import com.connectify.services.UserChatsService;
import com.connectify.dto.ContactsDTO;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.services.ContactService;
import com.connectify.dto.*;
import com.connectify.services.ContactsService;
import com.connectify.services.InvitationService;
import com.connectify.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    private final UserService userService;
    private final MessageService messageService;

    private final ChatService chatService;
    private final ContactService contactService;
    private final InvitationService invitationService;
    private final ContactsService contactsService;
    public ServerController() throws RemoteException {
        userService = new UserService();
        messageService = new MessageService();
        chatService = new ChatService();
        contactService =new ContactService();
        invitationService = new InvitationService();
        contactsService = new ContactsService();
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
        User userInfo = userService.getUserInfo(phoneNumber);
        contactsService.notifyContacts(phoneNumber, "A contact is offline.", userInfo.getName() + " has become offline");
        return userService.logoutUser(phoneNumber);
    }

    @Override
    public void registerConnectedUser(ConnectedUser user) throws RemoteException {
        userService.registerConnectedUser(user);
        User userInfo = userService.getUserInfo(user.getPhoneNumber());
        contactsService.notifyContacts(user.getPhoneNumber(), "A contact is online!", userInfo.getName() + " has become Online!");
    }

    @Override
    public List<ChatMemberDTO> getAllUserChats(String userId) throws RemoteException {
        var service = new UserChatsService();
        return service.getAllChats(userId);
    }

    @Override
    public List<ChatCardsInfoDTO> getUserChatsCardsInfo(String userId) throws RemoteException{
        var service = new UserChatsService();
        List<ChatCardsInfoDTO> chatCardsInfoDTOS = service.getAllChatsInfo(userId);
        return chatCardsInfoDTOS;
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

    @Override
    public boolean isPrivateChat(int chatID) throws RemoteException {
        return chatService.isPrivateChat(chatID);
    }

    @Override
    public List<MemberInfoDTO> getAllChatOtherMembersInfo(int chatId, String member) throws RemoteException {
        return chatService.getAllOtherMembersInfo(chatId,member);
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
    public boolean areAlreadyFriends(String userPhone, String friendPhone) throws RemoteException {
        return contactsService.areAlreadyFriends(userPhone, friendPhone);
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
