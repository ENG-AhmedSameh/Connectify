package com.connectify.controller;

import com.connectify.loaders.ViewLoader;
import com.connectify.utils.CountryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML private DatePicker birthDatePicker;
    @FXML private TextField emailTxtF;
    @FXML private ComboBox<Gender> genderComboBox;
    @FXML private ComboBox<Status> statusComboBox;
    @FXML private TextField nameTxtF;
    @FXML private TextField phoneNumTxtF;
    @FXML private AnchorPane profilePane;
    @FXML private TextArea bioTextArea;
    @FXML private Button editeBtn;
    @FXML private Label profileLbl;
    @FXML private Circle userImg;

    private String txtFieldsOriginalStyle, comboBoxOriginalStyle, datePickerOriginalStyle;

    private String testPhoneNumber = "+201143414035";
    private UserProfileResponse userProfileResponse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        initializeComboBox();
//        txtFieldsOriginalStyle = nameTxtF.getStyle();
//        comboBoxOriginalStyle = countryComboBox.getStyle();
//        datePickerOriginalStyle = birthDatePicker.getStyle();

        try {
            ServerAPI server = (ServerAPI) Client.registry.lookup("server");
            userProfileResponse = server.getUserProfile(testPhoneNumber);
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }

        Image image = new Image(new ByteArrayInputStream(userProfileResponse.getPicture()));
        userImg.setFill(new ImagePattern(image));

        bioTextArea.setText(userProfileResponse.getBio());
        phoneNumTxtF.setText(userProfileResponse.getPhoneNumber());
        nameTxtF.setText(userProfileResponse.getName());
        emailTxtF.setText(userProfileResponse.getEmail());
        birthDatePicker.setValue(userProfileResponse.getBirthDate());
        genderComboBox.setValue(userProfileResponse.getGender());
        statusComboBox.setValue(userProfileResponse.getStatus());
    }

//    private void initializeComboBox() {
//        initializeGenderComboBox();
//        initializeStatusComboBox();
//    }
//
//    private void initializeGenderComboBox(){
//        genderComboBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
//    }
//
//    private void initializeStatusComboBox(){
//        statusComboBox.getItems().addAll(Status.AVAILABLE, Status.BUSY, Status.AWAY);
//    }
//
//
//    private ListCell<String> createListCell() {
//        return new ListCell<>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                //setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%);");
//
//                if(!empty&&item!=null){
//                    String countryName = countryComboBox.getValue().toString();
//                    countryCodeLbl.setText(countryList.getCountriesMap().get(countryName));
//                    setText(countryName);
//                }
//            }
//        };
//    }

    @FXML
    private void editeBtnHandler(ActionEvent event){
        ViewLoader viewLoader = ViewLoader.getInstance();
        viewLoader.switchFromProfileToEditeProfile();
    }
}
