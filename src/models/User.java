package models;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class User extends ApplicationModel {
    private static final String USERS_TABLE = "users";
    public static final String USERS_ID = "id";
    private static final String USERS_FIRST_NAME = "first_name";
    private static final String USERS_LAST_NAME = "last_name";
    private static final String USERS_MIDDLE_NAME = "middle_name";
    private static final String USERS_LOGIN = "login";
    private static final String USERS_PASSWORD = "password";

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

    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String password;

    public User(String firstName, String lastName, String middleName, String login, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
    }

    public User() {
        super();
    }

    protected String getTable() {
        return USERS_TABLE;
    }

    public void signup() {
        List<String> columns = Arrays.asList(
            USERS_FIRST_NAME, USERS_LAST_NAME, USERS_MIDDLE_NAME, USERS_LOGIN, USERS_PASSWORD
        );
        List values = Arrays.asList(firstName, lastName, middleName, login, password);
        insertQuery(columns, values);
    }

    public ResultSet login() {
        List<String> columns = Arrays.asList(USERS_LOGIN, USERS_PASSWORD);
        List values = Arrays.asList(login, password);
        return selectQuery(columns, values);
    }
}
