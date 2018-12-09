package tableModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.Order;
import models.User;

import config.Translations;

public class UserTableItem {
    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty middleName;
    private StringProperty login;
    private StringProperty active;

    public static String ACTIVE = "АКТИВЕН";
    public static String INACTIVE = "ЗАБЛОКИРОВАН";

    public UserTableItem(User user) {
        this.id = new SimpleIntegerProperty(user.getId());
        this.firstName = new SimpleStringProperty(user.getFirstName());
        this.lastName = new SimpleStringProperty(user.getLastName());
        this.middleName = new SimpleStringProperty(user.getMiddleName());
        this.login = new SimpleStringProperty(user.getLogin());
        this.active = new SimpleStringProperty(user.isActive() ? ACTIVE : INACTIVE);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getActive() {
        return active.get();
    }

    public StringProperty activeProperty() {
        return this.active;
    }

    public void setActive(String active) {
        this.active.set(active);
    }

    public void triggerActive() {
        User user = new User();
        user.setId(id.get());
        user.triggerActive(active.get() == ACTIVE);
    }
}
