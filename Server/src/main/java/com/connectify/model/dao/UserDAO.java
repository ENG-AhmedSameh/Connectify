package com.connectify.model.dao;


import com.connectify.model.entities.User;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;

public interface UserDAO extends DAO<User, String> {
    boolean updatePicture(String phoneNumber, byte[] picture);
    boolean updatePassword(String phoneNumber, byte[] salt, String password);
    boolean updateMode(String phoneNumber, Mode mode);
    boolean updateStatus(String phoneNumber, Status status);
    boolean updateBio(String phoneNumber, String bio);
    Mode getMode(String phoneNumber);
    Status getStatus(String phoneNumber);
    boolean updateModeAndStatus(String phoneNumber,Mode mode, Status status);

    boolean updateToken(String phoneNumber, String token);

    String getPhoneNumberByToken(String token);
}
