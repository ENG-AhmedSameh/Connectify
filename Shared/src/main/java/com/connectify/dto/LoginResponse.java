package com.connectify.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private final boolean status;

    private final String message;

    private final String token;

    public LoginResponse(boolean status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
