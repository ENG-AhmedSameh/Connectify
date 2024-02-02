package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;
import com.connectify.loaders.ViewLoader;
import com.connectify.model.enums.Gender;
import com.connectify.model.enums.Status;
import com.connectify.util.PasswordManager;
import com.connectify.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileEditorController implements Initializable {

    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private PasswordField confirmPasswordPassF;
    @FXML
    private TextField emailTxtF;
    @FXML
    private ComboBox<Gender> genderComboBox;
    @FXML
    private ComboBox<Status> statusComboBox;
    @FXML
    private TextField nameTxtF;
    @FXML
    private PasswordField passwordPassF;
    @FXML
    private TextField phoneNumTxtF;
    @FXML
    private AnchorPane editeProfilePane;
    @FXML
    private Label editeProfileLbl;
    @FXML
    private Circle userImg;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private Button updateBtn;
    @FXML
    private Button cancelBtn;

    Boolean validInformation = true;
    private ServerAPI server;
    private String txtFieldsOriginalStyle, comboBoxOriginalStyle, datePickerOriginalStyle;
    private UserProfileResponse currentUserDetails;
    private boolean isPictureChanged;
    byte[] newPicture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();
        txtFieldsOriginalStyle = nameTxtF.getStyle();

        try {
            server = (ServerAPI) Client.getRegistry().lookup("server");
            currentUserDetails = server.getUserProfile("+20" +Client.getConnectedUser().getPhoneNumber());
            populateUserDetails();
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }
    }

    private void populateUserDetails() {
        setImage();
        bioTextArea.setText(currentUserDetails.getBio() == null ? "bio" : currentUserDetails.getBio());
        phoneNumTxtF.setText(currentUserDetails.getPhoneNumber());
        nameTxtF.setText(currentUserDetails.getName());
        emailTxtF.setText(currentUserDetails.getEmail());
        birthDatePicker.setValue(currentUserDetails.getBirthDate());
        genderComboBox.setValue(currentUserDetails.getGender());
        statusComboBox.setValue(currentUserDetails.getStatus());
    }

    private void setImage() {
        Image image = null;
        if (currentUserDetails.getPicture() == null) {
            image = new Image(String.valueOf(ProfileController.class.getResource("/images/profile.png")));
        } else {
            image = new Image(new ByteArrayInputStream(currentUserDetails.getPicture()));
        }
        userImg.setFill(new ImagePattern(image));
    }

    private void initializeComboBoxes() {
        genderComboBox.getItems().addAll(Gender.values());
        statusComboBox.getItems().addAll(Status.values());
    }

    @FXML
    void handleCancelAction(ActionEvent event) {
        StageManager.getInstance().switchToHome();
    }

    private void validateFields() {
        validateName();
        validateEmail();
//        validatePassword();
    }

    private boolean validatePassword() {
        if (!Objects.equals(confirmPasswordPassF.getText(), passwordPassF.getText())) {
            confirmPasswordPassF.setStyle("-fx-border-color: red;");
            confirmPasswordPassF.setTooltip(hintText("Doesn't match the password in the first field"));
            return false;
        } else {
            confirmPasswordPassF.setStyle(txtFieldsOriginalStyle);
            confirmPasswordPassF.setTooltip(null);
            return true;
        }
    }

    private void validateName() {
        String name = nameTxtF.getText();
        if (name.isEmpty()) {
            validInformation = false;
            nameTxtF.setTooltip(hintText("You must Enter your Name"));
            nameTxtF.setStyle("-fx-border-color: red;");
        } else if (name.length() > 50) {
            nameTxtF.setTooltip(hintText("Name is too long"));
            nameTxtF.setStyle("-fx-border-color: red;");
        } else if (!name.matches("^[a-zA-Z]+(?:\\s[a-zA-Z]+){0,4}$")) {
            nameTxtF.setTooltip(new Tooltip("Name must contains only english characters and maximum 4 spaces"));
            nameTxtF.setStyle("-fx-border-color: red;");
        } else {
            nameTxtF.setStyle(txtFieldsOriginalStyle);
            nameTxtF.setTooltip(null);
        }
    }

    private void validateEmail() {
        if (emailTxtF.getText().matches("[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}")) {
            emailTxtF.setStyle(txtFieldsOriginalStyle);
            emailTxtF.setTooltip(null);
        } else {
            emailTxtF.setStyle("-fx-border-color: red;");
            if (emailTxtF.getText().isEmpty())
                emailTxtF.setTooltip(hintText("You must enter your email"));
            else
                emailTxtF.setTooltip(hintText("Enter a valid email"));
        }
    }

    private Tooltip hintText(String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: rgba(241,241,241,1); -fx-text-fill: black; -fx-background-radius: 4; -fx-border-radius: 4; -fx-opacity: 1.0;");
        tooltip.setAutoHide(false);
        tooltip.setMaxWidth(300);
        tooltip.setWrapText(true);
        tooltip.setText(text);
        //tooltip.setGraphic(image);
        return tooltip;
    }

    @FXML
    void updateBtnHandler(ActionEvent event) {
        validateFields();
        if (validInformation) {
            if (isUserInfoChanged()) {
                UpdateUserInfoRequest updateUserInfoRequest = createUpdateUserInfoRequest();
                try {
                    server.updateUserProfile(updateUserInfoRequest);
                } catch (RemoteException e) {
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }

            if (passwordPassF.getText() != null && validatePassword()) {
                try {
                    byte[] salt = PasswordManager.generateSalt();
                    String password = PasswordManager.encode(passwordPassF.getText(), salt);
                    server.updateUserPassword(currentUserDetails.getPhoneNumber(), salt, password);
                } catch (RemoteException e) {
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }

            if (isPictureChanged) {
                try {
                    server.updateUserPicture(currentUserDetails.getPhoneNumber(), newPicture);
                } catch (RemoteException e) {
                    System.err.println("Remote Exception: " + e.getMessage());
                }
            }

            ViewLoader.getInstance().switchFromEditeProfileToProfile();
        }
    }

    private boolean isUserInfoChanged() {
        return nameTxtF.getText().equals(currentUserDetails.getName()) &&
                emailTxtF.getText().equals(currentUserDetails.getEmail()) &&
                genderComboBox.getValue().equals(currentUserDetails.getGender()) &&
                birthDatePicker.getValue().equals(currentUserDetails.getBirthDate()) &&
                bioTextArea.getText().equals(currentUserDetails.getBio()) &&
                statusComboBox.getValue().equals(currentUserDetails.getStatus());
    }

    private UpdateUserInfoRequest createUpdateUserInfoRequest() {
        UpdateUserInfoRequest request = new UpdateUserInfoRequest();
        request.setName(nameTxtF.getText());
        request.setEmail(emailTxtF.getText());
        request.setGender(genderComboBox.getValue());
        request.setBirthDate(birthDatePicker.getValue());
        request.setBio(bioTextArea.getText());
        request.setStatus(statusComboBox.getValue());
        return request;
    }

    @FXML
    void profileImageOnMouseClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image userImage = new Image(file.toURI().toURL().toString());
                userImg.setFill(new ImagePattern(userImage));

                newPicture = Files.readAllBytes(file.toPath());
                isPictureChanged = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
