package controllers;

import client.CustomClientMessage;
import client.CustomClientSocket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Button;

import java.io.IOException;
import java.io.Serializable;

import config.Routes;
import models.User;

import static config.Messages.*;

class ApplicationController {
    CustomClientSocket clientSocket;

    boolean loggedAsAdmin() {
//      TODO: use getcurrentuser
        return true;
//        return getCurrentUser().getRole().equals(User.ADMIN_ROLE);
    }

    private void switchWindow(Window currentWindow, String newWindowLocation) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fetchRoute(newWindowLocation)));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = (Stage)currentWindow;
        stage.setScene(new Scene(root));
    }

    void navigateFromButton(Button button, String newPath) {
        switchWindow(button.getScene().getWindow(), newPath);
    }

    private String fetchRoute(String route) {
        try {
            return Routes.class.getDeclaredField(route).get(null).toString();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }
}
