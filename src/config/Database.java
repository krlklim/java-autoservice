package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String USER = "root";
    private static final String PASSWORD = "kirylhasbigdick";
    private static final String NAME = "autoservice";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + NAME, USER, PASSWORD);
    }
}
