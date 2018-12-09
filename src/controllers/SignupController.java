package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import models.User;

import java.util.ArrayList;

public class SignupController extends ApplicationController {
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
    private Button signupBack;

    @FXML
    private TextField signupName;

    @FXML
    void initialize() {
        setupEvents();
    }

    private void setupEvents() {
        signupSubmit.setOnAction(event -> {
            signupUser();
            navigateFromButton(signupSubmit, "LOGIN_PATH");
            clearFields();
        });

        signupBack.setOnAction((event -> {
            navigateFromButton(signupBack, "LOGIN_PATH");
            clearFields();
        }));
    }

    private ArrayList<TextField> formButtons() {
        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(signupName);
        fields.add(signupLastName);
        fields.add(signupMiddleName);
        fields.add(signupLogin);
        fields.add(signupPassword);
        return fields;
    }

    private void clearFields() {
        for (TextField field : formButtons()) {
            field.clear();
        }
    }

    private void signupUser() {
        User user = new User(
            signupName.getText(),
            signupLastName.getText(),
            signupMiddleName.getText(),
            signupLogin.getText(),
            signupPassword.getText(),
            User.CUSTOMER_ROLE,
            true
        );
        user.signup();
    }
}

