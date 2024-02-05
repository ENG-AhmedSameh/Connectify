package com.connectify.Interfaces;

import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.dto.IncomingFriendInvitationResponse;

import com.connectify.dto.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectedUser extends Remote {
    void receiveNotification(String title, String body) throws RemoteException;

    String getPhoneNumber() throws RemoteException;

    void receiveMessage(MessageDTO messageDTO) throws RemoteException;

    void receiveFriendRequest(IncomingFriendInvitationResponse friendInvitation) throws RemoteException;

    void makeNewChatCard(ChatCardsInfoDTO chatCard) throws RemoteException;
}
