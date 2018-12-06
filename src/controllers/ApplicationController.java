package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Button;

import java.io.IOException;

import config.Routes;
import models.User;
import helpers.ApplicationContext;

class ApplicationController {
    void setCurrentUser(User user) {
        ApplicationContext.getInstance().setCurrentUser(user);
    }

    User getCurrentUser() {
        return ApplicationContext.getInstance().getCurrentUser();
    }

    boolean loggedAsAdmin() {
        return getCurrentUser().getRole().equals(User.ADMIN_ROLE);
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
