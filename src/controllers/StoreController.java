package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Automobile;
import tableModels.AutomobileTableModel;

import java.util.List;
import java.util.stream.Collectors;

public class StoreController extends ApplicationController {
    private void setAutomobiles(List<Automobile> automobiles) {
        automobilesTable.setItems(
            FXCollections.observableArrayList(
                automobiles.stream().map(AutomobileTableModel::new).collect(Collectors.toList())
            )
        );
    }

    @FXML
    private TableColumn<AutomobileTableModel, String> costColumn;

    @FXML
    private TableColumn<AutomobileTableModel, String> nameColumn;

    @FXML
    private TableColumn<AutomobileTableModel, String> productionYearColumn;

    @FXML
    private TableView<AutomobileTableModel> automobilesTable;

    @FXML
    private TableColumn<AutomobileTableModel, String> serialNumberColumn;

    @FXML
    private TableColumn<AutomobileTableModel, String> brandColumn;

    @FXML
    void initialize() {
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        serialNumberColumn.setCellValueFactory(cellData -> cellData.getValue().serialNumberProperty());
        productionYearColumn.setCellValueFactory(cellData -> cellData.getValue().productionYearProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty());

        setAutomobiles(Automobile.getAllAutomobiles());
    }
}
