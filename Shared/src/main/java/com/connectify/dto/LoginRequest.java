package com.connectify.dto;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String phoneNumber;

    private String password;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" + "phoneNumber='" + phoneNumber + '\'' + ", password='" + password + '\'' + '}';
    }
}
