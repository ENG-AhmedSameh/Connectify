package com.connectify.utils;

import com.connectify.model.enums.Gender;
import javafx.scene.control.*;

import java.util.Objects;

public class UserInformationValidator {

    private static boolean validInformation;
    private static String txtFieldsOriginalStyle, comboBoxOriginalStyle, datePickerOriginalStyle;

    public static boolean validateLoginForm(ComboBox<String> countryComboBox, TextField phoneNumTxtF, PasswordField passwordPassF){
        if(txtFieldsOriginalStyle == null){
            txtFieldsOriginalStyle = phoneNumTxtF.getStyle();
            comboBoxOriginalStyle = countryComboBox.getStyle();
        }
        validInformation = true;
        validateCountry(countryComboBox);
        validatePhoneNumber(phoneNumTxtF);
        validatePassword(passwordPassF);
        return validInformation;
    }
    public static boolean validateSignUpForm(ComboBox<String> countryComboBox, TextField phoneNumTxtF, TextField nameTxtF, TextField emailTxtF, PasswordField passwordPassF, PasswordField confirmPasswordPassF, ComboBox<Gender> genderComboBox, DatePicker birthDatePicker) {
        if(txtFieldsOriginalStyle==null){
            txtFieldsOriginalStyle = nameTxtF.getStyle();
            comboBoxOriginalStyle = countryComboBox.getStyle();
        }
        if(datePickerOriginalStyle==null)
            datePickerOriginalStyle = birthDatePicker.getStyle();
        validInformation = true;
        validateCountry(countryComboBox);
        validatePhoneNumber(phoneNumTxtF);
        validateName(nameTxtF);
        validateEmail(emailTxtF);
        validatePassword(passwordPassF);
        validatePasswordAndConfirmPassword(passwordPassF,confirmPasswordPassF);
        validGender(genderComboBox);
        return validInformation;
    }
    public static boolean validateUpdateProfileForm(TextField nameTxtF, TextField emailTxtF){
        if(txtFieldsOriginalStyle==null){
            txtFieldsOriginalStyle = nameTxtF.getStyle();
        }
        validInformation = true;
        validateName(nameTxtF);
        validateEmail(emailTxtF);
        return validInformation;
    }
    public static boolean validateUpdatedPassword(PasswordField passwordPassF, PasswordField confirmPasswordPassF){
        validInformation=true;
        validatePassword(passwordPassF);
        validatePasswordAndConfirmPassword(passwordPassF,confirmPasswordPassF);
        return validInformation;
    }

    private static void validGender(ComboBox<Gender> genderComboBox) {
        if(genderComboBox.getValue()==null) {
            validInformation = false;
            genderComboBox.setStyle("-fx-border-color: red;");
            genderComboBox.setTooltip(hintText("You must choose a gender"));
        }else {
            genderComboBox.setStyle(comboBoxOriginalStyle);
            genderComboBox.setTooltip(null);
        }
    }

    private static void validatePhoneNumber(TextField phoneNumTxtF) {
        if(!phoneNumTxtF.getText().matches("^(01|1)[0-2,5]{1}[0-9]{8}")){
            phoneNumTxtF.setStyle("-fx-border-color: red;");
            if(phoneNumTxtF.getText().isEmpty())
                phoneNumTxtF.setTooltip(hintText("You must enter your phone number"));
            else
                phoneNumTxtF.setTooltip(hintText("Enter a valid phone number"));
            validInformation = false;
        }else {
            phoneNumTxtF.setStyle(txtFieldsOriginalStyle);
            phoneNumTxtF.setTooltip(null);
        }
    }

    public static void validatePassword(PasswordField passwordPassF){
        if(passwordPassF.getText().length()<8){
            passwordPassF.setStyle("-fx-border-color: red;");
            passwordPassF.setTooltip(hintText("password must be at least 8 chars"));
            validInformation = false;
        }else{
            passwordPassF.setStyle(txtFieldsOriginalStyle);
            passwordPassF.setTooltip(null);
        }
    }

    private static void validatePasswordAndConfirmPassword(PasswordField passwordPassF, PasswordField confirmPasswordPassF) {

        if(!Objects.equals(confirmPasswordPassF.getText(), passwordPassF.getText())){
            confirmPasswordPassF.setStyle("-fx-border-color: red;");
            confirmPasswordPassF.setTooltip(hintText("Doesn't match the password in the first field"));
            validInformation = false;
        }else{
            confirmPasswordPassF.setStyle(txtFieldsOriginalStyle);
            confirmPasswordPassF.setTooltip(null);
        }
    }
    private static void validateCountry(ComboBox<String> countryComboBox) {
        if(countryComboBox.getValue()==null) {
            validInformation = false;
            countryComboBox.setStyle("-fx-border-color: red;");
            countryComboBox.setTooltip(hintText("You must choose a country"));
        }else {
            countryComboBox.setStyle(comboBoxOriginalStyle);
            countryComboBox.setTooltip(null);
        }
    }
    private static void validateName(TextField nameTxtF) {
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
    private static void validateEmail(TextField emailTxtF) {
        if(emailTxtF.getText().matches("[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}")){
            emailTxtF.setStyle(txtFieldsOriginalStyle);
            emailTxtF.setTooltip(null);
            validInformation = true;
        }else{
            emailTxtF.setStyle("-fx-border-color: red;");
            if(emailTxtF.getText().isEmpty())
                emailTxtF.setTooltip(hintText("You must enter your email"));
            else
                emailTxtF.setTooltip(hintText("Enter a valid email"));
            validInformation = false;
        }
    }

    public static Tooltip hintText(String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: rgba(241,241,241,1); -fx-text-fill: black; -fx-background-radius: 4; -fx-border-radius: 4; -fx-opacity: 1.0;");
        tooltip.setAutoHide(false);
        tooltip.setMaxWidth(300);
        tooltip.setWrapText(true);
        tooltip.setText(text);
        return tooltip;
    }
}
