package helpers;

import config.Database;
import models.User;

import java.sql.Connection;

public class ApplicationContext {
    private User currentUser = null;

    private final static ApplicationContext instance = new ApplicationContext();

    public static ApplicationContext getInstance() {
        return instance;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Connection getConnection() {
        try {
            return Database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
