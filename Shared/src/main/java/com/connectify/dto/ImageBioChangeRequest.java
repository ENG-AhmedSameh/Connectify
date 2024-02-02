package com.connectify.dto;

import java.io.File;
import java.io.Serializable;

public class ImageBioChangeRequest implements Serializable {

    private final String phoneNumber;
    private final File image;
    private final String bio;

    public ImageBioChangeRequest(String phoneNumber, File image, String bio) {
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public File getImage() {
        return image;
    }

    public String getBio() {
        return bio;
    }
}
