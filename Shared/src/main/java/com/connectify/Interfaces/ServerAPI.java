package com.connectify.Interfaces;


import com.connectify.dto.ContactsDTO;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.dto.SignUpRequest;
import com.connectify.dto.*;
import com.connectify.dto.*;
import com.connectify.model.entities.Attachments;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerAPI extends Remote{

    boolean signUp(SignUpRequest signUpRequest) throws RemoteException;
    List<ChatMemberDTO> getAllUserChats(String userId) throws RemoteException;
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
    List<ChatCardsInfoDTO> getUserChatsCardsInfo(String userId) throws RemoteException;

    ChatCardsInfoDTO getUserLastChatCardInfo(String userId) throws RemoteException;

    void changeProfileAndBio(ImageBioChangeRequest request) throws RemoteException;

    void sendMessage(MessageSentDTO message) throws RemoteException;

    void sendAttachment(MessageSentDTO messageSentDTO) throws RemoteException;

    File getAttachment(Integer attachmentId) throws RemoteException;

    void prepareCurrentChat(ChatMemberDTO chatMemberDTO) throws RemoteException;
    List<ContactsDTO> getContacts(String phoneNumber)throws RemoteException ;
    boolean isPrivateChat(int chatID) throws RemoteException;
    List<MemberInfoDTO> getAllChatOtherMembersInfo(int chatId,String member) throws RemoteException;
    List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String phoneNumber) throws RemoteException;

    boolean acceptFriendRequest(int invitationId) throws RemoteException;

    boolean cancelFriendRequest(int invitationId) throws RemoteException;

    boolean areAlreadyFriends(String userPhone, String friendPhone) throws RemoteException;
}
