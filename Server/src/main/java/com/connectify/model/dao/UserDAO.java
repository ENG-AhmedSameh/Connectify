package com.connectify.model.dao;


import com.connectify.model.entities.User;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;

import java.io.File;

public interface UserDAO extends DAO<User, String> {

    boolean updateMode(String phoneNumber, Mode mode);
    boolean updateStatus(String phoneNumber, Status status);
    boolean updateImage(String phoneNumber, File image);
    boolean updateBio(String phoneNumber, String bio);
}
