package controllers;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.CustomClientMessage;
import client.CustomClientSocket;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import models.User;

import static config.Messages.MESSAGE_RECEIVED;
import static config.Messages.MESSAGE_SENT;
import static config.Messages.USER_LOGIN;

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

        clientSocket = new CustomClientSocket();
        Thread th = new Thread(clientSocket);
        th.start();
    }

    private void setupEvents() {
        signupButton.setOnAction(event -> navigateFromButton(signupButton, "SIGNUP_PATH"));
        loginButton.setOnAction(event -> loginUser(loginField.getText().trim(), passwordField.getText().trim()));
    }

    private void loginUser(String loginText, String passwordText) {
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(passwordText);

        if (sendRequest(user, USER_LOGIN) == null) {
            return;
        }

        navigateFromButton(loginButton, "STORE_PATH");
    }

    User sendRequest(User user, String messageCode) {
        CustomClientMessage clientMessage = new CustomClientMessage(messageCode, user);

        try {
            System.out.println(MESSAGE_SENT + messageCode);
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
