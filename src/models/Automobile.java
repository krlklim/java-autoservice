package models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Automobile extends ApplicationModel {
    private static final String TABLE = "automobiles";
    public static final String ID = "id";
    private static final String BRAND = "brand";
    private static final String SERIAL_NUMBER = "serial_number";
    private static final String PRODUCTION_YEAR = "production_year";
    private static final String COST = "cost";
    private static final String NAME = "name";

    private int id;
    private String brand;
    private String serialNumber;
    private String productionYear;
    private BigDecimal cost;
    private String name;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static List<Automobile> getAllAutomobiles() {
        ResultSet result = selectAllQuery(TABLE);
        List<Automobile> automobiles = new ArrayList<>();
        try {
            while(result.next()) {
                Automobile automobile = new Automobile();

                automobile.setId(result.getInt(ID));
                automobile.setBrand(result.getString(BRAND));
                automobile.setCost(result.getBigDecimal(COST));
                automobile.setName(result.getString(NAME));
                automobile.setProductionYear(result.getString(PRODUCTION_YEAR));
                automobile.setSerialNumber(result.getString(SERIAL_NUMBER));

                automobiles.add(automobile);
            }
            return automobiles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
