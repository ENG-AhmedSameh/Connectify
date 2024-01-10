package com.connectify.controller;

import com.connectify.utils.CountryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class SignUpController implements Initializable {

    @FXML private DatePicker birthDatePicker;
    @FXML private PasswordField confirmPasswordPassF;
    @FXML private Label countryCodeLbl;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private TextField emailTxtF;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private ImageView logoImgView;
    @FXML private AnchorPane logoPane;
    @FXML private TextField nameTxtF;
    @FXML private PasswordField passwordPassF;
    @FXML private TextField phoneNumTxtF;
    @FXML private Button signUpBtn;
    @FXML private Label signUpLbl;
    @FXML private AnchorPane signUpPane;

    CountryList countryList;
    Boolean validInformation = true;

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
        genderComboBox.getItems().addAll("Male","Female");
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
    private void signUpBtnHandler(ActionEvent event){
        validateFields();
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


}
