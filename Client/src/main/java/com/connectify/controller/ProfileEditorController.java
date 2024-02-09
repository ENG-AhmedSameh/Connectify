package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.UpdateUserInfoRequest;
import com.connectify.dto.UserProfileResponse;
import com.connectify.loaders.ViewLoader;
import com.connectify.model.enums.Gender;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;
import com.connectify.util.PasswordManager;
import com.connectify.utils.CurrentUser;
import com.connectify.utils.RemoteManager;
import com.connectify.utils.StageManager;
import com.connectify.utils.UserInformationValidator;
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
    private ComboBox<String> genderComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private ComboBox<String> modeComboBox;
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
    private String txtFieldsOriginalStyle, comboBoxOriginalStyle, datePickerOriginalStyle;
    private UserProfileResponse currentUserDetails;
    private boolean isPictureChanged;
    private byte[] newPicture;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();
        txtFieldsOriginalStyle = nameTxtF.getStyle();
        try {
            currentUserDetails = RemoteManager.getInstance().getUserProfile(CurrentUser.getInstance().getPhoneNumber());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        populateUserDetails();

    }

    private void populateUserDetails() {
        setImage();
        bioTextArea.setText(currentUserDetails.getBio() == null ? "bio" : currentUserDetails.getBio());
        phoneNumTxtF.setText(currentUserDetails.getPhoneNumber());
        nameTxtF.setText(currentUserDetails.getName());
        emailTxtF.setText(currentUserDetails.getEmail());
        birthDatePicker.setValue(currentUserDetails.getBirthDate());
        genderComboBox.setValue(currentUserDetails.getGender().toString());
        statusComboBox.setValue(currentUserDetails.getStatus().toString());
        modeComboBox.setValue(currentUserDetails.getMode().toString());
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
        genderComboBox.getItems().addAll(Gender.MALE.toString(), Gender.FEMALE.toString());
        statusComboBox.getItems().addAll(Status.AVAILABLE.toString(), Status.BUSY.toString(), Status.AWAY.toString());
        modeComboBox.getItems().addAll(Mode.ONLINE.toString(), Mode.OFFLINE.toString());
    }

    @FXML
    void handleCancelAction(ActionEvent event) {
        StageManager.getInstance().switchToProfile();
    }


    @FXML
    void updateBtnHandler(ActionEvent event) {
        boolean validInformation = UserInformationValidator.validateUpdateProfileForm(nameTxtF,emailTxtF);
        if (validInformation) {
            if (isUserInfoChanged()) {
                UpdateUserInfoRequest updateUserInfoRequest = createUpdateUserInfoRequest();
                boolean result = RemoteManager.getInstance().updateUserProfile(updateUserInfoRequest);
                System.out.println("Update user profile result: " + result);
            }
            if(isModeOrStatusChanged()){
                boolean result = RemoteManager.getInstance().updateUserModeAndStatus(phoneNumTxtF.getText(),Mode.valueOf(modeComboBox.getValue()),Status.valueOf(statusComboBox.getValue()));
                System.out.println("Update user mode and status: "+result);
            }
            if (!(Objects.equals(passwordPassF.getText(), ""))) {
                boolean changeInPass = UserInformationValidator.validateUpdatedPassword(passwordPassF,confirmPasswordPassF);
                if(changeInPass){
                    byte[] salt = PasswordManager.generateSalt();
                    String password = PasswordManager.encode(passwordPassF.getText(), salt);
                    boolean result = RemoteManager.getInstance().updateUserPassword(currentUserDetails.getPhoneNumber(), salt, password);
                    System.out.println("Update password result: " + result);
                }
            }

            if (isPictureChanged) {
                boolean result = RemoteManager.getInstance().updateUserPicture(currentUserDetails.getPhoneNumber(), newPicture);
                System.out.println("Update profile picture result: " + result);
            }
        }

        StageManager.getInstance().switchToProfile();
    }

    private boolean isModeOrStatusChanged() {
        return !(statusComboBox.getValue().equals(currentUserDetails.getStatus().toString()) &&
                modeComboBox.getValue().equals(currentUserDetails.getMode().toString()));
    }
    private boolean isUserInfoChanged() {
        return !(nameTxtF.getText().equals(currentUserDetails.getName()) &&
                emailTxtF.getText().equals(currentUserDetails.getEmail()) &&
                genderComboBox.getValue().equals(currentUserDetails.getGender().toString()) &&
                birthDatePicker.getValue().equals(currentUserDetails.getBirthDate()) &&
                (bioTextArea.getText().equals("bio") || bioTextArea.getText().equals(currentUserDetails.getBio())));
    }


    private UpdateUserInfoRequest createUpdateUserInfoRequest() {
        UpdateUserInfoRequest request = new UpdateUserInfoRequest();

        request.setPhoneNumber(currentUserDetails.getPhoneNumber());
        request.setName(nameTxtF.getText());
        request.setEmail(emailTxtF.getText());
        request.setGender("Male".equalsIgnoreCase(genderComboBox.getValue()) ? Gender.MALE : Gender.FEMALE);
        request.setBirthDate(birthDatePicker.getValue());
        request.setBio(bioTextArea.getText());

        String statusString = statusComboBox.getValue();
        request.setStatus("Available".equalsIgnoreCase(statusString) ? Status.AVAILABLE :
                "Busy".equalsIgnoreCase(statusString) ? Status.BUSY : Status.AWAY);

        request.setMode("Online".equalsIgnoreCase(modeComboBox.getValue()) ? Mode.ONLINE : Mode.OFFLINE);

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
