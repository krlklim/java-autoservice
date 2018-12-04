package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Automobile;
import tableModels.AutomobileTableItem;

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
    private Button deleteAutomobileButton;

    @FXML
    void initialize() {
        setupCellValueFactories();
        setupEvents();
        resetAutomobiles();
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
        deleteAutomobileButton.setOnAction(event -> getSelectedItem().delete());
    }
}
