package com.connectify.dto;

import java.io.Serializable;

public class SignUpResponse implements Serializable {
    private boolean isSuccessful;

    private String message;

    private String token;

    public SignUpResponse() {}

    public SignUpResponse(boolean isSuccessful, String message, String token) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.token = token;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
