package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.Automobile;
import tableModels.AutomobileTableItem;

import java.math.BigDecimal;
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
    }

    private List<TextField> formFields() {
        return Arrays.asList(
            brandField,
            nameField,
            serialNumberField,
            productionYearField,
            costField
        );
    }

    private void clearForm() {
        for(TextField field: formFields()) {
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

    private void setupEvents() {
        deleteAutomobileButton.setOnAction(event -> deleteSelectedAutomobile());
        newAutomobileButton.setOnAction(event -> loadForm(CREATE_ACTION, CREATE_BUTTON_TEXT));
        editAutomobileButton.setOnAction(event -> loadForm(UPDATE_ACTION, UPDATE_BUTTON_TEXT));
        closeAutomobileFormButton.setOnAction(event -> hideForm());
        saveAutomobileButton.setOnAction(event -> saveAutomobile());
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

    private void deleteSelectedAutomobile() {
        getSelectedItem().delete();
        resetAutomobiles();
    }

    private void loadForm(String formAction, String sumbitButtonText) {
        this.formAction = formAction;
        saveAutomobileButton.setText(sumbitButtonText);
        prepareFormFields();
        showForm();
    }

    private void prepareFormFields() {
        switch(this.formAction) {
            case CREATE_ACTION:
                clearForm();
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
