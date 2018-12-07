package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class User extends ApplicationModel {
    private static final String TABLE = "users";
    public static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";

    public static final String ADMIN_ROLE = "admin";
    public static final String CUSTOMER_ROLE = "customer";

    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String password;
    private String role;

    public User() {
        super();
    }
    public User(String firstName, String lastName, String middleName, String login, String password, String role) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.equals(User.ADMIN_ROLE);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void signup() {
        List<String> columns = Arrays.asList(
            FIRST_NAME, LAST_NAME, MIDDLE_NAME, LOGIN, PASSWORD, ROLE
        );
        List values = Arrays.asList(firstName, lastName, middleName, login, password, role);
        insertQuery(TABLE, columns, values);
    }

    public static User login(String login, String password) {
        List<String> columns = Arrays.asList(LOGIN, PASSWORD);
        List values = Arrays.asList(login, password);
        ResultSet result = selectQuery(TABLE, columns, values);

        try {
            if(result.first()) {
                User user = fromSqlResult(result);
                user.setLogin(login);
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User find(int id) {
        ResultSet result = findQuery(TABLE, id);

        try {
            if (result.first()) {
                return fromSqlResult(result);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User fromSqlResult(ResultSet result) {
        try {
            User user = new User();
            user.setId(result.getInt(ID));
            user.setFirstName(result.getString(FIRST_NAME));
            user.setLastName(result.getString(LAST_NAME));
            user.setMiddleName(result.getString(MIDDLE_NAME));
            user.setRole(result.getString(ROLE));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
