package controllers;

import client.CustomClientMessage;
import client.CustomClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import models.User;

import java.io.IOException;
import java.util.ArrayList;

import static config.Messages.*;

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
        clientSocket = new CustomClientSocket();
        Thread th = new Thread(clientSocket);
        th.start();
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
            User.CUSTOMER_ROLE
        );

        sendRequest(user, USER_SIGNUP);
    }

    User sendRequest(User user, String messageCode) {
        CustomClientMessage clientMessage = new CustomClientMessage(messageCode, user);

        try {
            System.out.println(MESSAGE_SENT + messageCode);
            System.out.println(clientSocket);
            clientSocket.getObjectOutputStream().writeObject(clientMessage);

            CustomClientMessage serverMessage = (CustomClientMessage)clientSocket.getObjectInputStream().readObject();
            System.out.println(MESSAGE_RECEIVED + serverMessage.getMessage());

            User response = (User)serverMessage.getPayload();

            if (serverMessage.getMessage().equals("Failure")) {
                return null;
            }

            return response;
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}

