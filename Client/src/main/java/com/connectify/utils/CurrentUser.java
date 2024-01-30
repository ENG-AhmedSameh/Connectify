package com.connectify.utils;

import com.connectify.Interfaces.ConnectedUser;

import java.io.Serializable;

public class CurrentUser implements ConnectedUser, Serializable {

    private final String phoneNumber;

    public CurrentUser(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public void receiveAnnouncement(String announcement) throws Exception {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
