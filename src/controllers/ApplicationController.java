package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Button;

import java.io.IOException;

import config.Routes;

class ApplicationController {
    private void switchWindow(Window currentWindow, String newWindowLocation) {
//        currentWindow.hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fetchRoute(newWindowLocation)));

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
