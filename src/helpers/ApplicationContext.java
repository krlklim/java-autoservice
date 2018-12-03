package helpers;

import config.Database;
import models.User;

import java.sql.Connection;

public class ApplicationContext {
    private final static ApplicationContext instance = new ApplicationContext();

    private ApplicationContext() {
        try {
            this.connection = Database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        return connection;
    }

    public Connection connection;
    private User currentUser = null;
}
