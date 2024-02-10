package com.connectify.controller;

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
import java.time.LocalDate;
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
        initializeDatePicker();
        txtFieldsOriginalStyle = nameTxtF.getStyle();
        comboBoxOriginalStyle = countryComboBox.getStyle();
        datePickerOriginalStyle = birthDatePicker.getStyle();
    }

    private void initializeDatePicker() {
        birthDatePicker.setValue(LocalDate.of(1997, 1, 1));
        birthDatePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now().minusYears(6)) > 0 );
            }
        });
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
        validInformation = UserInformationValidator.validateSignUpForm(countryComboBox,phoneNumTxtF,nameTxtF,emailTxtF,passwordPassF,confirmPasswordPassF,genderComboBox,birthDatePicker);
        if(validInformation){
            SignUpRequest request = createSignUpRequest();
            SignUpResponse response = RemoteManager.getInstance().signUp(request);
            if (!response.isSuccessful()) {
                phoneNumTxtF.setTooltip(UserInformationValidator.hintText(response.getMessage()));
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

    public void onLoginLabelClickedHandler(MouseEvent mouseEvent) {
        clearFields();
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
