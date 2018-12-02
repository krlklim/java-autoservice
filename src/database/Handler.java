package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Handler {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(
                "jdbc:mysql://" + Config.host + ":" + Config.port + "/" + Config.name,
                Config.user,
                Config.password);
    }
}
