package com.connectify.controller;

import com.connectify.Client;
import com.connectify.Interfaces.ConnectedUser;
import com.connectify.dto.SignUpRequest;
import com.connectify.dto.SignUpResponse;
import com.connectify.model.enums.Gender;
import com.connectify.util.PasswordManager;
import com.connectify.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class SignUpController implements Initializable {

    @FXML private DatePicker birthDatePicker;
    @FXML private PasswordField confirmPasswordPassF;
    @FXML private Label countryCodeLbl;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private TextField emailTxtF;
    @FXML private ComboBox<Gender> genderComboBox;
    @FXML private TextField nameTxtF;
    @FXML private PasswordField passwordPassF;
    @FXML private TextField phoneNumTxtF;
    @FXML private ImageView logoImgView;
    @FXML private AnchorPane logoPane;
    @FXML private Button signUpBtn;
    @FXML private Label signUpLbl;
    @FXML private AnchorPane signUpPane;

    @FXML
    private Label errorLabel;

    private CountryList countryList;
    private Boolean validInformation = true;

    private String txtFieldsOriginalStyle, comboBoxOriginalStyle, datePickerOriginalStyle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBox();
        txtFieldsOriginalStyle = nameTxtF.getStyle();
        comboBoxOriginalStyle = countryComboBox.getStyle();
        datePickerOriginalStyle = birthDatePicker.getStyle();
    }

    private void initializeComboBox() {
        initializeCountryComboBox();
        initializeGenderComboBox();
    }
    private void initializeCountryComboBox() {
        countryList = CountryList.getInstance();
        countryComboBox.getItems().addAll(new TreeSet<>(countryList.getCountriesMap().keySet()));
        //countryComboBox.setButtonCell(createListCell(countryList));
    }

    private void initializeGenderComboBox(){
        genderComboBox.getItems().addAll(Gender.MALE,Gender.FEMALE);
    }

    @FXML
    private void countryOnSelectHandler(ActionEvent event){
        countryCodeLbl.setText(countryList.getCountriesMap().get(countryComboBox.getValue()));
    }

    private ListCell<String> createListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                //setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%);");

                if(!empty&&item!=null){
                    String countryName = countryComboBox.getValue().toString();
                    countryCodeLbl.setText(countryList.getCountriesMap().get(countryName));
                    setText(countryName);
                }
            }
        };
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

    @FXML
    private void signUpBtnHandler(ActionEvent event) throws RemoteException {
        validateFields();
        if(validInformation){
            SignUpRequest request = createSignUpRequest();
            SignUpResponse response = RemoteManager.getInstance().signUp(request);
            if (!response.isSuccessful()) {
                phoneNumTxtF.setTooltip(hintText(response.getMessage()));
                phoneNumTxtF.setStyle("-fx-border-color: red;");
                errorLabel.setText(response.getMessage());
                errorLabel.setVisible(true);
            }
            else {
                PropertiesManager.getInstance().setUserCredentials(response.getToken(), "true");
                PropertiesManager.getInstance().setLoginInformation(phoneNumTxtF.getText(), countryCodeLbl.getText(), countryComboBox.getValue());
                ConnectedUser user = CurrentUser.getInstance();
                RemoteManager.getInstance().registerConnectedUser(user);
                errorLabel.setVisible(false);
                clearFields();
                StageManager.getInstance().switchToSecondSignUp();
            }
        }
    }

    private void validateFields() {
        validateCountry();
//        validatePhoneNumber();
        validateName();
        validateEmail();
        validatePassword();
//        validGender();
    }

    private void validatePassword() {
        if(!Objects.equals(confirmPasswordPassF.getText(), passwordPassF.getText())){
            confirmPasswordPassF.setStyle("-fx-border-color: red;");
            confirmPasswordPassF.setTooltip(hintText("Doesn't match the password in the first field"));
        }else{
            confirmPasswordPassF.setStyle(txtFieldsOriginalStyle);
            confirmPasswordPassF.setTooltip(null);
        }

    }


    private void validateCountry() {
        if(countryComboBox.getValue()==null) {
            validInformation = false;
            countryComboBox.setStyle("-fx-border-color: red;");
            countryComboBox.setTooltip(hintText("You must choose a country"));
        }
    }
    private void validateName() {
        String name = nameTxtF.getText();
        if(name.isEmpty()){
            validInformation = false;
            nameTxtF.setTooltip(hintText("You must Enter your Name"));
            nameTxtF.setStyle("-fx-border-color: red;");
        }else if (name.length()>50) {
            nameTxtF.setTooltip(hintText("Name is too long"));
            nameTxtF.setStyle("-fx-border-color: red;");
        }else if(!name.matches("^[a-zA-Z]+(?:\\s[a-zA-Z]+){0,4}$")){
            nameTxtF.setTooltip(new Tooltip("Name must contains only english characters and maximum 4 spaces"));
            nameTxtF.setStyle("-fx-border-color: red;");
        }else{
            nameTxtF.setStyle(txtFieldsOriginalStyle);
            nameTxtF.setTooltip(null);
        }
    }
    private void validateEmail() {
        if(emailTxtF.getText().matches("[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}")){
            emailTxtF.setStyle(txtFieldsOriginalStyle);
            emailTxtF.setTooltip(null);
        }else{
            emailTxtF.setStyle("-fx-border-color: red;");
            if(emailTxtF.getText().isEmpty())
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


    public void onLoginLabelClickedHandler(MouseEvent mouseEvent) {
        StageManager.getInstance().switchToLogin();
    }

    private SignUpRequest createSignUpRequest(){
        SignUpRequest request = new SignUpRequest();
        request.setPhoneNumber(countryCodeLbl.getText() + phoneNumTxtF.getText());
        request.setName(nameTxtF.getText());
        request.setEmail(emailTxtF.getText());
        request.setSalt(PasswordManager.generateSalt());
        request.setPassword(PasswordManager.encode(passwordPassF.getText(), request.getSalt()));
        request.setGender(genderComboBox.getValue());
        request.setCountry(countryComboBox.getValue());
        request.setBirthDate(birthDatePicker.getValue());
        return request;
    }


    private void clearFields(){
        nameTxtF.clear();
        emailTxtF.clear();
        phoneNumTxtF.clear();
        passwordPassF.clear();
        confirmPasswordPassF.clear();
        countryCodeLbl.setText(countryList.getCountriesMap().get(countryComboBox.getValue()));
        nameTxtF.setStyle(txtFieldsOriginalStyle);
        phoneNumTxtF.setStyle(txtFieldsOriginalStyle);
        countryComboBox.setStyle(comboBoxOriginalStyle);
        birthDatePicker.setStyle(datePickerOriginalStyle);
        validInformation = true;
    }

}
