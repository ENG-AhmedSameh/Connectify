package com.connectify.utils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesManager{

    private static PropertiesManager instance;
    private final Properties userCredentials;

    private final Properties loginInformation;

    private PropertiesManager() {
        userCredentials = new Properties();
        loginInformation = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("user.properties")) {
            userCredentials.load(is);
        } catch (IOException ex) {
            System.err.println("Could load user credentials: " + ex.getMessage());;
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("login.properties")) {
            loginInformation.load(is);
        } catch (IOException ex) {
            System.err.println("Could load login information: " + ex.getMessage());;
        }
    }

    public static PropertiesManager getInstance(){
        if(instance == null){
            instance = new PropertiesManager();
        }
        return instance;
    }

    public void setUserCredentials(String token, String autoLogin){
        userCredentials.setProperty("token", token);
        userCredentials.setProperty("autoLogin", autoLogin);
        storeCredentials();
    }


    public void setUserCredentials(String autoLogin) {
        String token = userCredentials.getProperty("token");
        userCredentials.setProperty("token", token);
        userCredentials.setProperty("autoLogin", autoLogin);
        storeCredentials();
    }


    public String getToken(){
        return userCredentials.getProperty("token");
    }

    public String getAutoLogin(){
        return userCredentials.getProperty("autoLogin");
    }

    public void setLoginInformation(String phoneNumber, String countryCode, String country){
        loginInformation.setProperty("phoneNumber", phoneNumber);
        loginInformation.setProperty("countryCode", countryCode);
        loginInformation.setProperty("country", country);
        storeLoginInformation();
    }

    public String getPhoneNumber() {
        return loginInformation.getProperty("phoneNumber");
    }

    public String getCountryCode(){
        return loginInformation.getProperty("countryCode");
    }

    public String getCountry(){
        return loginInformation.getProperty("country");
    }

    private void storeLoginInformation(){
        try (FileOutputStream fos = new FileOutputStream("Client/target/classes/login.properties")) {
            loginInformation.store(fos, null);
        } catch (IOException ex) {
            System.err.println("Couldn't save credentials to file: " + ex.getMessage());
        }
    }


    private void storeCredentials(){
        try (FileOutputStream fos = new FileOutputStream("Client/target/classes/user.properties")) {
            userCredentials.store(fos, null);
        } catch (IOException ex) {
            System.err.println("Couldn't save credentials to file: " + ex.getMessage());
        }
    }



}
