package models;

import java.util.Arrays;
import java.util.List;

public class Order extends ApplicationModel {
    private static final String TABLE = "orders";
    public static final String ID = "id";
    private static final String PAYMENT = "payment";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String CONFIRMED = "confirmed";
    private static final String AUTOMOBILE_ID = "automobile_id";
    private static final String USER_ID = "user_id";

    private int id;
    private String payment;
    private String address;
    private String phone;
    private boolean confirmed;
    private int automobile_id;
    private int user_id;

    public Order() {
        super();
    }
    public Order(String payment, String address, String phone, boolean confirmed, int automobile_id, int user_id) {
        super();
        this.payment = payment;
        this.address = address;
        this.phone = phone;
        this.confirmed = confirmed;
        this.automobile_id = automobile_id;
        this.user_id = user_id;
    }

    public void create() {
        List<String> columns = Arrays.asList(PAYMENT, ADDRESS, PHONE, CONFIRMED, AUTOMOBILE_ID, USER_ID);
        List values = Arrays.asList(payment, address, phone, confirmed ? 1 : 0, automobile_id, user_id);
        insertQuery(TABLE, columns, values);
    }

    public void update() {
        List<String> columns = Arrays.asList(PAYMENT, ADDRESS, PHONE, CONFIRMED, AUTOMOBILE_ID, USER_ID);
        List values = Arrays.asList(payment, address, phone, confirmed ? 1 : 0, automobile_id, user_id);
        updateQuery(TABLE, id, columns, values);
    }

    public void confirm() {
        List<String> columns = Arrays.asList(CONFIRMED);
        List values = Arrays.asList(1);
        updateQuery(TABLE, id, columns, values);
        this.confirmed = true;
    }
}
