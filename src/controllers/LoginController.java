package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import models.User;

public class LoginController extends ApplicationController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signupButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    void initialize() {
        setupEvents();
    }

    private void setupEvents() {
        signupButton.setOnAction(event -> navigateFromButton(signupButton, "SIGNUP_PATH"));
        loginButton.setOnAction(event -> {
            loginUser(loginField.getText().trim(), passwordField.getText().trim());
            if (getCurrentUser() == null) {
                return;
            }
            navigateFromButton(loginButton, "STORE_PATH");
        });
    }

    private void loginUser(String loginText, String passwordText) {
        if(loginText.isEmpty() || passwordText.isEmpty()) {
            return;
        }

        User user = User.login(loginText, passwordText);
        setCurrentUser(user);
    }
}
