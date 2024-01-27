package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ServerAPI;
import com.connectify.dto.UserRequest;
import com.connectify.loaders.ViewLoader;
import com.connectify.model.enums.Gender;
import com.connectify.model.enums.Status;
import com.connectify.utils.CountryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeSet;

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
    private UserRequest userRequest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        initializeComboBox();
//        txtFieldsOriginalStyle = nameTxtF.getStyle();
//        comboBoxOriginalStyle = countryComboBox.getStyle();
//        datePickerOriginalStyle = birthDatePicker.getStyle();

        try {
            ServerAPI server = (ServerAPI) Client.registry.lookup("server");
            userRequest = server.getUser(testPhoneNumber);
        } catch (RemoteException e) {
            System.err.println("Remote Exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
        }

        Image image = new Image(new ByteArrayInputStream(userRequest.getPicture()));
        userImg.setFill(new ImagePattern(image));

        bioTextArea.setText(userRequest.getBio());
        phoneNumTxtF.setText(userRequest.getPhoneNumber());
        nameTxtF.setText(userRequest.getName());
        emailTxtF.setText(userRequest.getEmail());
        birthDatePicker.setValue(userRequest.getBirthDate());
        genderComboBox.setValue(userRequest.getGender());
        statusComboBox.setValue(userRequest.getStatus());
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
