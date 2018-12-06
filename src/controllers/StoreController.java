package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import models.Automobile;
import models.Order;
import tableModels.AutomobileTableItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoreController extends ApplicationController {
    @FXML
    private TableColumn<AutomobileTableItem, String> costColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> nameColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> productionYearColumn;

    @FXML
    private TableView<AutomobileTableItem> automobilesTable;

    @FXML
    private TableColumn<AutomobileTableItem, String> serialNumberColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> brandColumn;

    @FXML
    private Button newAutomobileButton;

    @FXML
    private Button editAutomobileButton;

    @FXML
    private Button deleteAutomobileButton;

    @FXML
    private Button saveAutomobileButton;

    @FXML
    private Button closeAutomobileFormButton;

    @FXML
    private Pane automobileForm;

    @FXML
    private TextField brandField;

    @FXML
    private TextField productionYearField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField costField;

    @FXML
    private TextField serialNumberField;

    @FXML
    private Pane adminControlPane;

    @FXML
    private TabPane storeTabPane;

    @FXML
    private Tab automobilesTab;

    @FXML
    private Tab ordersTab;

    @FXML Button orderButton;

    @FXML
    private Label orderHeader;

    @FXML
    private Pane orderForm;

    @FXML
    private TextField paymentField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Button confirmOrderButton;

    @FXML Button cancelOrderSaveButton;

    private String formAction;

    private static final String CREATE_BUTTON_TEXT = "Создать";
    private static final String UPDATE_BUTTON_TEXT = "Сохранить";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";

    @FXML
    void initialize() {
        setupCellValueFactories();
        setupEvents();
        resetAutomobiles();
        hideForm();
        setupUserControls();
    }

    private void setupEvents() {
        deleteAutomobileButton.setOnAction(event -> deleteSelectedAutomobile());
        newAutomobileButton.setOnAction(event -> loadAutomobilesForm(CREATE_ACTION, CREATE_BUTTON_TEXT));
        editAutomobileButton.setOnAction(event -> loadAutomobilesForm(UPDATE_ACTION, UPDATE_BUTTON_TEXT));
        closeAutomobileFormButton.setOnAction(event -> hideForm());
        saveAutomobileButton.setOnAction(event -> saveAutomobile());
        automobilesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> triggerControlButtons(!(newValue == null))
        );
        orderButton.setOnAction(event -> loadOrdersForm());
        confirmOrderButton.setOnAction((event -> createOrder()));
        cancelOrderSaveButton.setOnAction(event -> closeOrdersForm());
    }

    private void loadOrdersForm() {
        automobilesTable.setDisable(true);
        orderForm.setVisible(true);
        orderHeader.setText(
            getSelectedItem().getBrand() + " " +
            getSelectedItem().getName() + " " +
            getSelectedItem().getSerialNumber()
        );
    }

    private void closeOrdersForm() {
        orderForm.setVisible(false);
        clearForm(orderFormFields());
        automobilesTable.setDisable(false);
    }

    private void createOrder() {
        orderFromForm().create();
        closeOrdersForm();
    }

    private List<Button> controlButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(newAutomobileButton);
        buttons.add(editAutomobileButton);
        buttons.add(deleteAutomobileButton);
        return buttons;
    }

    private void setupUserControls() {
        if (loggedAsAdmin()) {
            adminControlPane.setVisible(true);
            orderButton.setVisible(false);
        } else {
            adminControlPane.setVisible(false);
            orderButton.setVisible(true);
            storeTabPane.getTabs().remove(ordersTab);
        }
    }

    private List<TextField> automobileFormFields() {
        return Arrays.asList(
            brandField,
            nameField,
            serialNumberField,
            productionYearField,
            costField
        );
    }

    private List<TextField> orderFormFields() {
        return Arrays.asList(
            paymentField,
            phoneField,
            addressField
        );
    }

    private void clearForm(List<TextField> fields) {
        for(TextField field: fields) {
            field.clear();
        }
    }

    private void showForm() {
        triggerForm(true);
    }

    private void hideForm() {
        triggerForm(false);
    }

    private void triggerForm(boolean visible) {
        triggerControlButtons(!visible);
        automobilesTable.setDisable(visible);
        automobileForm.setVisible(visible);
    }

    private void resetAutomobiles() {
        setAutomobiles(Automobile.selectAll());
    }

    private void setAutomobiles(List<Automobile> automobiles) {
        automobilesTable.setItems(
            FXCollections.observableArrayList(
                automobiles.stream().map(AutomobileTableItem::new).collect(Collectors.toList())
            )
        );
    }

    private void setupCellValueFactories() {
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        serialNumberColumn.setCellValueFactory(cellData -> cellData.getValue().serialNumberProperty());
        productionYearColumn.setCellValueFactory(cellData -> cellData.getValue().productionYearProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty());
    }

    private AutomobileTableItem getSelectedItem() {
        return automobilesTable.getSelectionModel().getSelectedItem();
    }

    private void triggerControlButtons(boolean enabled) {
        for(Button button: controlButtons()) {
            button.setDisable(!enabled);
        }
    }

    private void saveAutomobile() {
        switch (formAction) {
            case "create":
                createAutomobile();
                break;
            case "update":
                updateAutomobile();
                break;
            default:
                break;
        }
        resetAutomobiles();
        triggerForm(false);
    }

    private void createAutomobile() {
        automobileFromForm().create();
    }

    private void updateAutomobile() {
        Automobile automobile = automobileFromForm();
        automobile.setId(getSelectedItem().getId());
        automobile.update();
    }

    private Automobile automobileFromForm() {
        return new Automobile(
            brandField.getText(),
            nameField.getText(),
            serialNumberField.getText(),
            productionYearField.getText(),
            new BigDecimal(costField.getText())
        );
    }

    private Order orderFromForm() {
        return new Order(
            paymentField.getText(),
            addressField.getText(),
            phoneField.getText(),
            false,
            getSelectedItem().getId(),
            getCurrentUser().getId()
        );
    }

    private void deleteSelectedAutomobile() {
        getSelectedItem().delete();
        resetAutomobiles();
    }

    private void loadAutomobilesForm(String formAction, String sumbitButtonText) {
        this.formAction = formAction;
        saveAutomobileButton.setText(sumbitButtonText);
        prepareFormFields();
        automobilesTable.setDisable(true);
        showForm();
    }

    private void prepareFormFields() {
        switch(this.formAction) {
            case CREATE_ACTION:
                clearForm(automobileFormFields());
                break;
            case UPDATE_ACTION:
                populateFormFields();
                break;
            default:
                break;
        }
    }

    private void populateFormFields() {
        AutomobileTableItem automobile = getSelectedItem();

        brandField.setText(automobile.getBrand());
        nameField.setText(automobile.getName());
        serialNumberField.setText(automobile.getSerialNumber());
        productionYearField.setText(automobile.getProductionYear());
        costField.setText(automobile.getCost());
    }
}
