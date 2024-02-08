package com.connectify.Interfaces;

import com.connectify.dto.ChatCardsInfoDTO;

import com.connectify.dto.MessageDTO;
import com.connectify.model.enums.Status;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectedUser extends Remote {
    void receiveNotification(String title, String body) throws RemoteException;

    String getPhoneNumber() throws RemoteException;

    void receiveMessage(MessageDTO messageDTO) throws RemoteException;

    void updateContactModeToOffline(String phoneNumber) throws RemoteException;

    void updateContactStatus(String phoneNumber, Status status) throws RemoteException;

    void makeNewChatCard(ChatCardsInfoDTO chatCard) throws RemoteException;

    void forceLogout() throws RemoteException;

    void ping() throws RemoteException;
}
