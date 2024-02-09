package com.connectify.utils;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

public class ImageConverter {

    public static ImagePattern convertBytesToImagePattern(byte[] imageBytes) {
        Image image;
        if (imageBytes == null || imageBytes.length == 0) {
            image = new Image(Objects.requireNonNull(ImageConverter.class.getResourceAsStream("/images/profile.png")));
            return new ImagePattern(image);
        }
        try {
            image = new Image(new ByteArrayInputStream(imageBytes));
            return new ImagePattern(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
