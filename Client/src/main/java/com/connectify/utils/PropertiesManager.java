package com.connectify.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesManager {

    private static PropertiesManager instance;
    String TOKEN_FILE = System.getProperty("user.dir") + File.separator + "config" + File.separator + "user.properties";
    String LOGIN_FILE = System.getProperty("user.dir") + File.separator + "config" + File.separator + "login.properties";
    private static final String TOKEN_KEY = "token";
    private static final String AUTO_LOGIN_KEY = "autoLogin";
    private static final String PHONE_NUMBER_KEY = "phoneNumber";
    private static final String COUNTRY_CODE_KEY = "countryCode";
    private static final String COUNTRY_KEY = "country";

    private final Properties tokenProperties;

    private final Properties loginProperties;

    private PropertiesManager() {
        tokenProperties = new Properties();
        loginProperties = new Properties();
        String pathString = System.getProperty("user.dir") + File.separator + "config";
        Path path = Paths.get(pathString);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.err.println("Failed to create config directory");
            }
        }
        loadProperties();
    }

    public static PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream inputStream = new FileInputStream(TOKEN_FILE)) {
            tokenProperties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Could not find token file");
        }

        try (InputStream inputStream = new FileInputStream(LOGIN_FILE)) {
            loginProperties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Could not find login file");
        }
    }

    private void saveTokenProperties() {
        try (OutputStream outputStream = new FileOutputStream(TOKEN_FILE)) {
            tokenProperties.store(outputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save token properties");
        }
    }

    private void saveLoginProperties() {
        try (OutputStream outputStream = new FileOutputStream(LOGIN_FILE)) {
            loginProperties.store(outputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to load login properties");
        }
    }

    public void setUserCredentials(String token, String autoLogin) {
        tokenProperties.setProperty(TOKEN_KEY, token);
        tokenProperties.setProperty(AUTO_LOGIN_KEY, autoLogin);
        saveTokenProperties();
    }

    public void setUserCredentials(String autoLogin) {
        tokenProperties.setProperty(TOKEN_KEY, getToken());
        tokenProperties.setProperty(AUTO_LOGIN_KEY, autoLogin);
        saveTokenProperties();
    }

    public String getToken() {
        return tokenProperties.getProperty(TOKEN_KEY);
    }

    public String getAutoLogin() {
        return tokenProperties.getProperty(AUTO_LOGIN_KEY);
    }

    public void setLoginInformation(String phoneNumber, String countryCode, String country) {
        loginProperties.setProperty(PHONE_NUMBER_KEY, phoneNumber);
        loginProperties.setProperty(COUNTRY_CODE_KEY, countryCode);
        loginProperties.setProperty(COUNTRY_KEY, country);
        saveLoginProperties();
    }

    public String getPhoneNumber() {
        return loginProperties.getProperty(PHONE_NUMBER_KEY);
    }

    public String getCountryCode() {
        return loginProperties.getProperty(COUNTRY_CODE_KEY);
    }

    public String getCountry() {
        return loginProperties.getProperty(COUNTRY_KEY);
    }
}
