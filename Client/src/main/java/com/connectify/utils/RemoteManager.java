package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.*;

import org.controlsfx.control.Notifications;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;


public class RemoteManager {

    private ServerAPI server;

    private final int port = 1099;

    private final String host = "localhost";
    private static RemoteManager instance;


    private RemoteManager(){
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            server = (ServerAPI) registry.lookup("server");
        } catch (RemoteException | NotBoundException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }


    public static RemoteManager getInstance() {
        if (instance == null) {
            instance = new RemoteManager();
        }
        return instance;
    }

    public boolean signUp(SignUpRequest request){
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.signUp(request);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public LoginResponse login(LoginRequest request) {
        if(isServerDown()){
            handleServerDown();
            return new LoginResponse(false, "Server is down");
        }
        try {
            return server.login(request);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public void registerConnectedUser(ConnectedUser connectedUser) {
        if(isServerDown()){
            handleServerDown();
            return;
        }
        try {
            server.registerConnectedUser(connectedUser);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    public void logout(ConnectedUser connectedUser) {
        if(isServerDown()){
            handleServerDown();
            return;
        }
        try {
            server.logout(connectedUser.getPhoneNumber());
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }


    public UserProfileResponse getUserProfile(String phoneNumber) {
        if(isServerDown()){
            handleServerDown();
            return new UserProfileResponse();
        }
        try {
            return server.getUserProfile(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public boolean updateUserProfile(UpdateUserInfoRequest updateUserInfoRequest) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.updateUserProfile(updateUserInfoRequest);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUserPassword(String phoneNumber, byte[] salt, String password) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.updateUserPassword(phoneNumber, salt, password);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUserPicture(String phoneNumber, byte[] newPicture) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.updateUserPicture(phoneNumber, newPicture);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }


    public List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String currentUserPhone) {
        if(isServerDown()){
            handleServerDown();
            return new ArrayList<>();
        }
        try {
            return server.getIncomingFriendRequests(currentUserPhone);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public void changeProfileAndBio(ImageBioChangeRequest request) {
        if(isServerDown()){
            handleServerDown();
            return;
        }
        try {
            server.changeProfileAndBio(request);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    public void sendMessage(MessageSentDTO messageSentDTO) {
        if(isServerDown()){
            handleServerDown();
            return;
        }
        try {
            server.sendMessage(messageSentDTO);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    public List<ContactsDTO> getContacts(String phoneNumber) {
        if(isServerDown()){
            handleServerDown();
            return new ArrayList<>();
        }
        try {
            return server.getContacts(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public List<ChatCardsInfoDTO> getUserChatsCardsInfo(String phoneNumber) {
        if(isServerDown()){
            handleServerDown();
            return new ArrayList<>();
        }
        try {
            return server.getUserChatsCardsInfo(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public boolean areAlreadyFriends(String currentUserPhone, String text) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.areAlreadyFriends(currentUserPhone, text);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public FriendToAddResponse getFriendToAdd(String newContactPhone) {
        if(isServerDown()){
            handleServerDown();
            return new FriendToAddResponse();
        }
        try {
            return server.getFriendToAdd(newContactPhone);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public boolean isInvitationSent(String currentUserPhone, String text) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.isInvitationSent(currentUserPhone, text);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean sendInvitation(String currentUserPhone, String phoneNumber) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.sendInvitation(currentUserPhone, phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public Boolean isPrivateChat(int chatID) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.isPrivateChat(chatID);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public List<MemberInfoDTO> getAllChatOtherMembersInfo(int chatID, String phoneNumber) {
        if(isServerDown()){
            handleServerDown();
            return new ArrayList<>();
        }
        try {
            return server.getAllChatOtherMembersInfo(chatID, phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public void prepareCurrentChat(ChatMemberDTO chatMemberDTO) {
        if(isServerDown()){
            handleServerDown();
            return;
        }
        try {
            server.prepareCurrentChat(chatMemberDTO);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    public boolean acceptFriendRequest(int invitationId) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.acceptFriendRequest(invitationId);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelFriendRequest(int invitationId) {
        if(isServerDown()){
            handleServerDown();
            return false;
        }
        try {
            return server.cancelFriendRequest(invitationId);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public void sendAttachment(MessageSentDTO messageSentDTO) {
        if(isServerDown()){
            handleServerDown();
            return;
        }
        try {
            server.sendAttachment(messageSentDTO);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    private void handleServerDown() {
        showServerDownNotification();
        StageManager.getInstance().switchToLogin();
    }

    private void showServerDownNotification() {
        Notifications
                .create()
                .title("Server is down")
                .text("Server is down. contact the admin and try again later")
                .showInformation();
    }

    public boolean isServerDown() {
        return server == null;
    }

    public ChatCardsInfoDTO getUserLastChatCardInfo(String phoneNumber) {
        try {
            return server.getUserLastChatCardInfo(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }
}
