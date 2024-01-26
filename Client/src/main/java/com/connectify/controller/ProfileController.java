package com.connectify.controller;

import com.connectify.loaders.ViewLoader;
import com.connectify.utils.CountryList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class ProfileController implements Initializable {

    @FXML private DatePicker birthDatePicker;
    @FXML private Label countryCodeLbl;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private TextField emailTxtF;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private ComboBox<String> modeComboBox;
    @FXML private TextField nameTxtF;
    @FXML private PasswordField passwordPassF;
    @FXML private TextField phoneNumTxtF;
    @FXML private AnchorPane profilePane;
    @FXML private TextArea bioTextArea;
    @FXML private Button editeBtn;
    @FXML private Label profileLbl;
    @FXML private Circle userImg;

    CountryList countryList;

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
    private void editeBtnHandler(ActionEvent event){

    }
}
