package models;

import helpers.ApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order extends ApplicationModel {
    private static final String TABLE = "orders";
    private static final String ID = "id";
    private static final String PAYMENT = "payment";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String CONFIRMED = "confirmed";
    private static final String AUTOMOBILE_ID = "automobile_id";
    private static final String USER_ID = "user_id";

    public static String CONFIRMED_STATUS = "ПОДТВЕРЖДЕН";
    public static String UNCONFIRMED_STATUS = "В ОБРАБОТКЕ";

    private int id;
    private String payment;
    private String address;
    private String phone;
    private boolean confirmed;
    private Automobile automobile;
    private User user;

    public Order() {
        super();
    }
    public Order(String payment, String address, String phone, boolean confirmed, Automobile automobile, User user) {
        super();
        this.payment = payment;
        this.address = address;
        this.phone = phone;
        this.confirmed = confirmed;
        this.automobile = automobile;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean getConfirmed() {
        return this.confirmed;
    }

    public String getConfirmedString() {
        return this.confirmed ? CONFIRMED_STATUS : UNCONFIRMED_STATUS;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Automobile getAutomobile() {
        return this.automobile;
    }

    public void setAutomobile(Automobile automobile) {
        this.automobile = automobile;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void create() {
        List<String> columns = Arrays.asList(PAYMENT, ADDRESS, PHONE, CONFIRMED, AUTOMOBILE_ID, USER_ID);
        List values = Arrays.asList(payment, address, phone, confirmed ? 1 : 0, automobile.getId(), user.getId());
        insertQuery(TABLE, columns, values);
    }

    public void update() {
        List<String> columns = Arrays.asList(PAYMENT, ADDRESS, PHONE, CONFIRMED, AUTOMOBILE_ID, USER_ID);
        List values = Arrays.asList(payment, address, phone, confirmed ? 1 : 0, automobile.getId(), user.getId());
        updateQuery(TABLE, id, columns, values);
    }

    public void confirm() {
        List<String> columns = Arrays.asList(CONFIRMED);
        List values = Arrays.asList(1);
        updateQuery(TABLE, id, columns, values);
        this.confirmed = true;
    }

    public static List<Order> selectVisible() {
        User currentUser = ApplicationContext.getInstance().getCurrentUser();

        if (currentUser.isAdmin()) {
            return selectAll();
        } else {
            return selectBy(USER_ID, Integer.toString(currentUser.getId()));
        }
    }

    public static List<Order> selectBy(String column, String value) {
        ResultSet result = findByQuery(TABLE, column, value);
        List<Order> orders = new ArrayList<>();

        try {
            while(result.next()) {
                Order order = fromSqlResult(result);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Order fromSqlResult(ResultSet result) {
        try {
            Order order = new Order();

            order.setId(result.getInt(ID));
            order.setPayment(result.getString(PAYMENT));
            order.setAddress(result.getString(ADDRESS));
            order.setPhone(result.getString(PHONE));
            order.setAutomobile(Automobile.find(Integer.parseInt(result.getString(AUTOMOBILE_ID))));
            order.setUser(User.find(Integer.parseInt(result.getString(USER_ID))));
            order.setConfirmed(result.getInt(CONFIRMED) == 1);

            return order;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<Order> selectAll() {
        ResultSet result = selectAllQuery(TABLE);
        List<Order> orders = new ArrayList<>();
        try {
            while(result.next()) {
                Order order = fromSqlResult(result);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
