package com.connectify.dto;

import java.io.Serializable;

public class IncomingFriendInvitationResponse implements Serializable {
    private String phoneNumber;
    private String name;
    private byte[] picture;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

}
