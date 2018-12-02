package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import models.User;

public class SignupController {

    @FXML
    private TextField signupLastName;

    @FXML
    private TextField signupLogin;

    @FXML
    private PasswordField signupPassword;

    @FXML
    private TextField signupMiddleName;

    @FXML
    private Button signupSubmit;

    @FXML
    private TextField signupName;

    @FXML
    void initialize() {
        signupSubmit.setOnAction(event -> {
            User user = new User(
                signupName.getText(),
                signupLastName.getText(),
                signupMiddleName.getText(),
                signupLogin.getText(),
                signupPassword.getText()
            );
            user.signup();
        });
    }
}

