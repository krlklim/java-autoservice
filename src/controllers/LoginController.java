package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {
    private static final String SIGNUP_PATH = "/views/signup/index.fxml";

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
        setupButtonEvents();
    }

    private void setupButtonEvents() {
        signupButton.setOnAction(event -> switchWindow(signupButton.getScene().getWindow(), SIGNUP_PATH));
        loginButton.setOnAction(event -> loginUser(loginField.getText().trim(), passwordField.getText().trim()));
    }

    private void switchWindow(Window currentWindow, String newWindowLocation) {
        currentWindow.hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(newWindowLocation));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void loginUser(String loginText, String passwordText) {
        if(loginText.isEmpty() || passwordText.isEmpty()) { }
    }
}
