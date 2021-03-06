package tableModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.Automobile;

import java.math.BigDecimal;

public class AutomobileTableItem {
    private IntegerProperty id;
    private StringProperty brand;
    private StringProperty serialNumber;
    private StringProperty productionYear;
    private StringProperty cost;
    private StringProperty name;

    public AutomobileTableItem(Automobile automobile) {
        this.id = new SimpleIntegerProperty(automobile.getId());
        this.brand = new SimpleStringProperty(automobile.getBrand());
        this.name = new SimpleStringProperty(automobile.getName());
        this.serialNumber = new SimpleStringProperty(automobile.getSerialNumber());
        this.productionYear = new SimpleStringProperty(automobile.getProductionYear());
        this.cost = new SimpleStringProperty(automobile.getCost().toString());
    }

    public Automobile getAutomobile() {
        Automobile automobile = new Automobile(
            getBrand(), getName(), getSerialNumber(), getProductionYear(), new BigDecimal(getCost())
        );
        automobile.setId(getId());
        return automobile;
    }

    public void delete() {
        Automobile automobile = new Automobile();
        automobile.setId(getId());
        automobile.delete();
    }

    public void update(String brand, String name, String serialNumber, String productionYear, BigDecimal cost) {
        Automobile automobile = new Automobile(brand, name, serialNumber, productionYear, cost);
        automobile.setId(getId());
        automobile.update();
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public StringProperty serialNumberProperty() {
        return serialNumber;
    }

    public String getProductionYear() {
        return productionYear.get();
    }

    public StringProperty productionYearProperty() {
        return productionYear;
    }

    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }
}
