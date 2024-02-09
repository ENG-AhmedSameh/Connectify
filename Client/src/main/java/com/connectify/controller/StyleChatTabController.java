package com.connectify.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;

public class StyleChatTabController {
    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ChoiceBox<String> fontSizeChoiceBox;

    @FXML
    private ChoiceBox<String> fontTypeChoiceBox;

    @FXML
    private TextArea textArea;

    @FXML
    private ToggleButton boldButton;

    @FXML
    private ToggleButton italicButton;

    @FXML
    private ToggleButton underlineButton;

    @FXML
    private void initialize() {
        textArea.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        fontSizeChoiceBox.setValue("14");
        fontTypeChoiceBox.setValue("Arial");
        colorPicker.setOnAction(event -> changeTextColor(colorPicker.getValue()));
        fontSizeChoiceBox.setOnAction(event -> changeFontSize(Integer.parseInt(fontSizeChoiceBox.getValue())));

        boldButton.setOnAction(event -> toggleBold());
        italicButton.setOnAction(event -> toggleItalic());
        underlineButton.setOnAction(event -> toggleUnderline());

        fontTypeChoiceBox.setOnAction(event -> changeFontType(fontTypeChoiceBox.getValue()));
    }

    private void changeTextColor(Color color) {
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        String boldStyle = boldButton.isSelected() ? "-fx-font-weight: bold;" : "";
        String italicStyle = italicButton.isSelected() ? "-fx-font-style: italic;" : "";
        String underlineStyle = underlineButton.isSelected() ? "text-decoration: underline;" : "";
        textArea.setStyle(boldStyle + italicStyle + underlineStyle + "-fx-text-fill: " + hex + ";" + getFontSizeStyle());
    }

    private void changeFontType(String fontType) {
        Color color = colorPicker.getValue();
        String textColor = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        String boldStyle = boldButton.isSelected() ? "-fx-font-weight: bold;" : "";
        String italicStyle = italicButton.isSelected() ? "-fx-font-style: italic;" : "";
        String underlineStyle = underlineButton.isSelected() ? "text-decoration: underline;" : "";

        String updatedStyle = "-fx-font-family: '" + fontType + "';" +
                boldStyle + italicStyle + underlineStyle +
                "-fx-text-fill: " + textColor + ";" +
                getFontSizeStyle();

        textArea.setStyle(updatedStyle);
    }


    private String getFontSizeStyle() {
        if (textArea.getStyle().contains("-fx-font-size")) {
            String currentStyle = textArea.getStyle();
            String[] styleParts = currentStyle.split("-fx-font-size: ");
            if (styleParts.length > 1) {
                String sizeValue = styleParts[1].split(";")[0];
                return "-fx-font-size: " + sizeValue + ";";
            }
        }
        return "";
    }

    private void changeFontSize(int newSize) {
        String textColor = textArea.getStyle().contains("-fx-text-fill") ? textArea.getStyle().split("-fx-text-fill: ")[1].split(";")[0] : "black";
        String boldStyle = boldButton.isSelected() ? "-fx-font-weight: bold;" : "";
        String italicStyle = italicButton.isSelected() ? "-fx-font-style: italic;" : "";
        String underlineStyle = underlineButton.isSelected() ? "text-decoration: underline;" : "";
        textArea.setStyle(boldStyle + italicStyle + underlineStyle + "-fx-font-size: " + newSize + "px; -fx-text-fill: " + textColor + ";");
    }

    private void toggleBold() {
        changeTextColor(colorPicker.getValue());
    }

    private void toggleItalic() {
        changeTextColor(colorPicker.getValue());
    }

    private void toggleUnderline() {
        changeTextColor(colorPicker.getValue());
    }
}

