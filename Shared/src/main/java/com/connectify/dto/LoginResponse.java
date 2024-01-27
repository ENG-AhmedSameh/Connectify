package com.connectify.dto;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private final boolean status;

    private final String message;

    public LoginResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
