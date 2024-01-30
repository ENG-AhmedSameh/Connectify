package com.connectify.controller;


import com.connectify.Client;
import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.LoginRequest;
import com.connectify.dto.LoginResponse;
import com.connectify.loaders.ViewLoader;
import com.connectify.utils.CountryList;
import com.connectify.utils.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class LoginController implements Initializable {

    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private Label countryCodeLabel;

    @FXML
    private Label errorLabel;
    private CountryList countryList = CountryList.getInstance();

    private ServerAPI server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.getItems().addAll(new TreeSet<>(countryList.getCountriesMap().keySet()));
    }

    @Deprecated
    public void SignupButtonOnAction(ActionEvent e) {
        ViewLoader loader = ViewLoader.getInstance();
        loader.switchFromLoginToSignUpScreen();
    }
    @Deprecated
    public void LoginButtonOnAction(ActionEvent e) {
        if(countryComboBox.getValue()==null){
            countryComboBox.setTooltip(new Tooltip("Please select your country"));
            countryComboBox.setStyle("-fx-border-color: red;");
            return;
        }
        if(phoneNumberTextField.getText().isBlank() || !phoneNumberTextField.getText().matches("^[0-9]+$")){
            phoneNumberTextField.setTooltip(new Tooltip("Please enter a valid phone number"));
            phoneNumberTextField.setStyle("-fx-border-color: red;");
            return;
        }
        if(passwordTextField.getText().isBlank()) {
            passwordTextField.setTooltip(new Tooltip("Please enter your password"));
            passwordTextField.setStyle("-fx-border-color: red;");
            return;
        }
        LoginRequest request = createLoginRequest();
        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
            LoginResponse response = server.login(request);
            if (response.getStatus()) {
                ConnectedUser connectedUser = new CurrentUser(phoneNumberTextField.getText());
                server.registerForAnnoucements(connectedUser);
                Client.setConnectedUser(connectedUser);
                ViewLoader.getInstance().switchToHomeScreen();
            }
            else {
                errorLabel.setText(response.getMessage());
                errorLabel.setVisible(true);
            }
        } catch (RemoteException ex) {
            System.err.println("Remote Exception: " + ex.getMessage());
        } catch (NotBoundException ex) {
            System.err.println("NotBoundException: " + ex.getMessage());
        }
    }

    private LoginRequest createLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setPhoneNumber(countryCodeLabel.getText() + phoneNumberTextField.getText());
        request.setPassword(passwordTextField.getText());
        return request;
    }

    @FXML
    private void countryOnSelectHandler(ActionEvent event){
        countryCodeLabel.setText(countryList.getCountriesMap().get(countryComboBox.getValue()));
    }

    @FXML
    private void countryOnKeyPressedHandler(KeyEvent event) {
        String keyPressed = event.getText().toLowerCase();
        if (keyPressed.length() == 1 && countryComboBox.isShowing()) {
            for (String item : countryComboBox.getItems()) {
                if (item.toLowerCase().startsWith(keyPressed)) {
                    countryComboBox.getSelectionModel().select(item);
                    countryComboBox.getEditor().setText(item);
                    countryComboBox.getEditor().positionCaret(item.length());
                    KeyEvent arrowUp = createKeyEvent(KeyEvent.KEY_PRESSED, KeyCode.UP);
                    KeyEvent arrowDown = createKeyEvent(KeyEvent.KEY_PRESSED, KeyCode.DOWN);
                    countryComboBox.fireEvent(arrowUp);
                    countryComboBox.fireEvent(arrowDown);
                    event.consume();
                    break;
                }
            }
        };
    }

    private KeyEvent createKeyEvent(javafx.event.EventType<KeyEvent> eventType, KeyCode keyCode) {
        return new KeyEvent(
                eventType,
                "",
                "",
                keyCode,
                false,
                false,
                false,
                false
        );
    }

}