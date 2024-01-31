package com.connectify.Interfaces;

import java.rmi.Remote;

public interface ConnectedUser extends Remote {
    void receiveAnnouncement(String announcement) throws Exception;

    String getPhoneNumber();
}
