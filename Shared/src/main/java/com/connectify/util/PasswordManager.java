package com.connectify.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PasswordManager {
    public static String encode(String password, byte[] salt){
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        byte[] hash = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            System.err.println("Hashing Exception: " + e.getMessage());
            return null;
        }
        return new String(hash,  StandardCharsets.UTF_8);
    }

    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static boolean isEqual(String hashedPassword, String password, byte[] salt){
        return hashedPassword.equals(encode(password, salt));
    }
}
