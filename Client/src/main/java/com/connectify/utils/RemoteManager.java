package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.*;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;
import org.controlsfx.control.Notifications;


import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;


public class RemoteManager {

    private ServerAPI server;

    private final static int port = 1099;

    private final static String host = "localhost";
    private static RemoteManager instance;


    private RemoteManager(){}

    public static RemoteManager getInstance() {
        if(instance != null && instance.isServerDown()) {
            instance.handleServerDown();
            instance = null;
        }
        if (instance == null) {
            instance = new RemoteManager();
            try {
//                Registry registry = LocateRegistry.getRegistry("192.168.1.11", port); //the server ip in the network
                Registry registry = LocateRegistry.getRegistry(host, port);
                instance.server = (ServerAPI) registry.lookup("server");
            } catch (RemoteException | NotBoundException e) {
                instance.handleServerDown();
                System.err.println("Remote Exception: " + e.getMessage());
            }
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public SignUpResponse signUp(SignUpRequest request){
        if(isServerDown()){
            reset();
            return new SignUpResponse(false, "Server is Down", null);
        }
        try {
            return server.signUp(request);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return new SignUpResponse(false, "Server is Down", null);
        }
    }

    public LoginResponse login(LoginRequest request) {
        if(isServerDown()){
            reset();
            return new LoginResponse(false, "Server is down", null);
        }
        try {
            return server.login(request);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return new LoginResponse(false, "Server is down", null);
        }
    }

    public void registerConnectedUser(ConnectedUser connectedUser) {
        if(isServerDown()){
            reset();
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
            reset();
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
            reset();
            return null;
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
            reset();
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
            reset();
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
            reset();
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
            reset();
            return null;
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
            reset();
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
            reset();
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
            reset();
            return null;
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
            reset();
            return null;
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
            reset();
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
            reset();
            return null;
        }
        try {
            return server.getFriendToAdd(newContactPhone);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public int isInvitationSent(String currentUserPhone, String text) {
        if(isServerDown()){
            reset();
            return -1;
        }
        try {
            return server.isInvitationSent(currentUserPhone, text);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return -1;
        }
    }

    public boolean sendInvitation(String currentUserPhone, String phoneNumber) {
        if(isServerDown()){
            reset();
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
            reset();
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
            reset();
            return null;
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
            reset();
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
            reset();
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
            reset();
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
            reset();
            return;
        }
        try {
            server.sendAttachment(messageSentDTO);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    public boolean isServerDown() {
        return server == null;
    }

    public ChatCardsInfoDTO getUserLastChatCardInfo(String phoneNumber) {
        if(isServerDown()){
            reset();
            return null;
        }
        try {
            return server.getUserLastChatCardInfo(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public byte[] getAttachment(Integer attachmentID) {
        if(isServerDown()){
            reset();
            return null;
        }
        try {
            return server.getAttachment(attachmentID);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public Mode getContactMode(String phoneNumber) {
        if(isServerDown()){
            reset();
            return null;
        }
        try {
            return server.getUserMode(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.out.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }
    public Status getContactStatus(String phoneNumber) {
        if(isServerDown()){
            reset();
            return null;
        }
        try {
            return server.getUserStatus(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.out.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public boolean updateUserModeAndStatus(String phoneNumber,Mode mode, Status status) {
        if(isServerDown()){
            reset();
            return false;
        }
        try {
            return server.updateUserModeAndStatus(phoneNumber,mode,status);
        }catch (RemoteException e){
            handleServerDown();
            System.out.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean createGroup(List<ContactsDTO> contactsDTOS, String groupName, String groupDescription, byte[] image) {
        if(isServerDown()){
            reset();
            return false;
        }
        try {
            return server.createGroup(contactsDTOS, groupName, groupDescription, image);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return false;
        }
    }

    public String getPhoneNumberByToken(String token) {
        if(isServerDown()){
            reset();
            return null;
        }
        try {
            return server.getPhoneNumberByToken(token);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
            return null;
        }
    }

    public void sendPingBack(String phoneNumber) {
        if(isServerDown()){
            reset();
            return;
        }
        try {
            server.sendPingBack(phoneNumber);
        } catch (RemoteException e) {
            handleServerDown();
            System.err.println("Remote Exception: " + e.getMessage());
        }
    }

    private void handleServerDown() {
        NotificationsManager.showErrorNotification();
        server = null;
        Platform.runLater(() -> StageManager.getInstance().switchToLogin());
    }
}
