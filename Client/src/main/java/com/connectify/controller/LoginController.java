package com.connectify.controller;


import com.connectify.loaders.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField PhoneNumberTextField;
    @FXML private PasswordField PasswordTextField;
    @FXML private Label loginmessagelabel;

    @FXML
    public void SignupButtonOnAction(ActionEvent e)
    {
        ViewLoader loader = ViewLoader.getInstance();
        BorderPane mainPane = loader.getMainBorderPane();
        GridPane centerPane =(GridPane)mainPane.getCenter();
        centerPane.getChildren().remove(loader.getLoginAnchorPane());
        centerPane.add(loader.getSignUpAnchorPane(), 1, 0);
    }
    public void LoginButtonOnAction(ActionEvent e)
    {
        boolean x= false;
        for(int i=0;i<PhoneNumberTextField.getText().length();i++)
        {
            if(Character.getNumericValue(PhoneNumberTextField.getText().charAt(i))>9||Character.getNumericValue(PhoneNumberTextField.getText().charAt(i))<0)
            {
                x=true;
            }
        }
        if(PhoneNumberTextField.getText().isBlank()&&PasswordTextField.getText().isBlank())
        {
            loginmessagelabel.setText("Enter Your PhoneNumber And Password!");
        }
        else if(PhoneNumberTextField.getText().isBlank())
        {
            loginmessagelabel.setText("PhoneNumber is Empty");
        }
        else if(x)
        {
            loginmessagelabel.setText("PhoneNumber Should Be Integer Numbers");
        }
        else if(PhoneNumberTextField.getText().length()!=10)
        {
            loginmessagelabel.setText("You PhoneNumber Should be 11 Numbers");
        }
        else if(PasswordTextField.getText().isBlank())
        {
            loginmessagelabel.setText("Password is Empty");
        }
        else
        {
            loginmessagelabel.setText("");

        }
    }
}