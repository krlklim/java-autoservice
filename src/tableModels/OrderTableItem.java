package tableModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.Automobile;
import models.Order;
import models.User;

import java.math.BigDecimal;

public class OrderTableItem {
    private IntegerProperty id;
    private StringProperty automobile;
    private StringProperty user;
    private StringProperty payment;
    private StringProperty address;
    private StringProperty phone;
    private StringProperty cost;
    private StringProperty confirmed;

    public OrderTableItem(Order order) {
        this.id = new SimpleIntegerProperty(order.getId());
        this.payment = new SimpleStringProperty(order.getPayment());
        this.address = new SimpleStringProperty(order.getAddress());
        this.phone = new SimpleStringProperty(order.getPhone());
        this.confirmed = new SimpleStringProperty(order.getConfirmedString());
        this.cost = new SimpleStringProperty(order.getAutomobile().getCost().toString());
        this.automobile = new SimpleStringProperty(automobileName(order.getAutomobile()));
        this.user = new SimpleStringProperty(userName(order.getUser()));
    }

    public void confirm() {
        Order order = new Order();
        order.setId(id.get());
        order.confirm();
    }

    private String automobileName(Automobile automobile) {
        return String.join(" ",
            automobile.getBrand(),
            automobile.getName(),
            automobile.getSerialNumber()
        );
    }

    private String userName(User user) {
        return String.join(" ", user.getLastName(), user.getFirstName());
    }

    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost.set(cost);
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

    public String getAutomobile() {
        return automobile.get();
    }

    public StringProperty automobileProperty() {
        return automobile;
    }

    public void setAutomobile(String automobile) {
        this.automobile.set(automobile);
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getPayment() {
        return payment.get();
    }

    public StringProperty paymentProperty() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment.set(payment);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getConfirmed() {
        return confirmed.get();
    }

    public boolean getBooleanConfirmed() {
        return confirmed.get().equals(Order.CONFIRMED_STATUS);
    }

    public StringProperty confirmedProperty() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed.set(confirmed);
    }
}
