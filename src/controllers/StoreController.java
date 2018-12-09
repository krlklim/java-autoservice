package controllers;

import com.itextpdf.layout.Document;
import config.Translations;
import helpers.ApplicationContext;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import models.Automobile;
import models.Order;
import models.User;
import tableModels.AutomobileTableItem;
import tableModels.OrderTableItem;
import tableModels.UserTableItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class StoreController extends ApplicationController {
    private static final String CREATE_BUTTON_TEXT = "Создать";
    private static final String UPDATE_BUTTON_TEXT = "Сохранить";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";

    private String formAction;

//  Status panel
    @FXML
    private Label roleLabel;

    @FXML
    private Button logoutButton;

//  Tabs
    @FXML
    private TabPane storeTabPane;

    @FXML
    private Tab automobilesTab;

    @FXML
    private Tab ordersTab;

    @FXML
    private Tab usersTab;

//  Automobiles table
    @FXML
    private TableView<AutomobileTableItem> automobilesTable;

    @FXML
    private TableColumn<AutomobileTableItem, String> costColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> nameColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> productionYearColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> serialNumberColumn;

    @FXML
    private TableColumn<AutomobileTableItem, String> brandColumn;

//  Automobiles admin controls
    @FXML
    private Pane adminControlPane;

    @FXML
    private Button newAutomobileButton;

    @FXML
    private Button editAutomobileButton;

    @FXML
    private Button deleteAutomobileButton;

//  Automobiles customer controls
    @FXML
    Button orderButton;

//  Automobiles form
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

//  Automobiles form controls

    @FXML
    private Button saveAutomobileButton;

    @FXML
    private Button closeAutomobileFormButton;

//  Orders table
    @FXML
    private TableView<OrderTableItem> ordersTable;

    @FXML
    private TableColumn<OrderTableItem, String> automobileColumn;

    @FXML
    private TableColumn<OrderTableItem, String> userColumn;

    @FXML
    private TableColumn<OrderTableItem, String> paymentColumn;

    @FXML
    private TableColumn<OrderTableItem, String> addressColumn;

    @FXML
    private TableColumn<OrderTableItem, String> phoneColumn;

    @FXML
    private TableColumn<OrderTableItem, String> orderCostColumn;

    @FXML
    private TableColumn<OrderTableItem, String> confirmedColumn;

//  Orders form
    @FXML
    private Pane orderForm;

    @FXML
    private Label orderHeader;

    @FXML
    private TextField paymentField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

//  Orders admin controls
    @FXML
    private Button confirmOrderButton;

    @FXML
    private Button exportCheckoutButton;

//  Orders form controls

    @FXML
    private Button saveOrderButton;

    @FXML
    Button cancelOrderSaveButton;

//  Users table
    @FXML
    private TableView<UserTableItem> usersTable;

    @FXML
    private TableColumn<UserTableItem, String> userLoginColumn;

    @FXML
    private TableColumn<UserTableItem, String> userFirstNameColumn;

    @FXML
    private TableColumn<UserTableItem, String> userLastNameColumn;

    @FXML
    private TableColumn<UserTableItem, String> userMiddleNameColumn;

//  Users controls
    @FXML
    private Button deleteUserButton;

//  Cars chart
    @FXML
    private Tab statsTab;

    @FXML
    private LineChart carsChart;

    @FXML
    private CategoryAxis carsChartXAxis;

    @FXML
    private NumberAxis carsChartYAxis;

    @FXML
    void initialize() {
        setupHeader();
        setupCellValueFactories();
        setupEvents();
        resetAutomobiles();
        resetOrders();
        resetUsers();
        hideForm();
        setupUserControls();
        setupCarsChart();
    }

//  Data binding

    private void setupCellValueFactories() {
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        serialNumberColumn.setCellValueFactory(cellData -> cellData.getValue().serialNumberProperty());
        productionYearColumn.setCellValueFactory(cellData -> cellData.getValue().productionYearProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty());

        automobileColumn.setCellValueFactory(cellData -> cellData.getValue().automobileProperty());
        orderCostColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty());
        paymentColumn.setCellValueFactory(cellData -> cellData.getValue().paymentProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        confirmedColumn.setCellValueFactory(cellData -> cellData.getValue().confirmedProperty());

        userLoginColumn.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
        userFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        userLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        userMiddleNameColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
    }

    private void resetAutomobiles() {
        setAutomobiles(Automobile.selectAll());
        resetChart();
    }

    private void setAutomobiles(List<Automobile> automobiles) {
        automobilesTable.setItems(
            FXCollections.observableArrayList(
                automobiles.stream().map(AutomobileTableItem::new).collect(Collectors.toList())
            )
        );
    }

    private void resetOrders() {
        setOrders(Order.selectVisible());
    }

    private void setOrders(List<Order> orders) {
       ordersTable.setItems(
            FXCollections.observableArrayList(
                orders.stream().map(OrderTableItem::new).collect(Collectors.toList())
            )
        );
    }

    private void resetUsers() {
        setUsers(User.selectAll());
    }

    private void setUsers(List<User> users) {
        usersTable.setItems(
                FXCollections.observableArrayList(
                        users.stream().map(UserTableItem::new).collect(Collectors.toList())
                )
        );
    }


    private void setupEvents() {
        deleteAutomobileButton.setOnAction(event -> deleteSelectedAutomobile());
        newAutomobileButton.setOnAction(event -> loadAutomobilesForm(CREATE_ACTION, CREATE_BUTTON_TEXT));
        editAutomobileButton.setOnAction(event -> loadAutomobilesForm(UPDATE_ACTION, UPDATE_BUTTON_TEXT));
        closeAutomobileFormButton.setOnAction(event -> hideForm());
        saveAutomobileButton.setOnAction(event -> saveAutomobile());
        orderButton.setOnAction(event -> loadOrdersForm());
        saveOrderButton.setOnAction((event -> createOrder()));
        cancelOrderSaveButton.setOnAction(event -> closeOrdersForm());
        confirmOrderButton.setOnAction(event -> confirmOrder());
        deleteUserButton.setOnAction(event -> deleteUser());
        logoutButton.setOnAction(event -> logoutUser());
        exportCheckoutButton.setOnAction(event -> exportCheckout());

        automobilesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> triggerAutomobileControlButtons(!(newValue == null))
        );

        ordersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> triggerOrderControlButtons(!(newValue == null))
        );

        usersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> triggerUserDeleteButton(!(newValue == null))
        );

    }

    private void logoutUser() {
        setCurrentUser(null);
        navigateFromButton(logoutButton, "LOGIN_PATH");
    }

    private void loadOrdersForm() {
        automobilesTable.setDisable(true);
        orderForm.setVisible(true);
        orderHeader.setText(
            getSelectedAutomobile().getBrand() + " " +
            getSelectedAutomobile().getName() + " " +
            getSelectedAutomobile().getSerialNumber()
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

    private List<Button> automobileControlButtons() {
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
            confirmOrderButton.setVisible(true);
            exportCheckoutButton.setVisible(true);
        } else {
            adminControlPane.setVisible(false);
            orderButton.setVisible(true);
            confirmOrderButton.setVisible(false);
            storeTabPane.getTabs().remove(usersTab);
            exportCheckoutButton.setVisible(false);
        }

        ordersTable.setDisable(false);
    }

    private void setupHeader() {
        User user = getCurrentUser();
        roleLabel.setText(
            Translations.roles.get(user.getRole()) + " " + user.getFirstName() + " " + user.getLastName()
        );

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
        triggerAutomobileControlButtons(!visible);
        automobilesTable.setDisable(visible);
        automobileForm.setVisible(visible);
    }

    private AutomobileTableItem getSelectedAutomobile() {
        return automobilesTable.getSelectionModel().getSelectedItem();
    }

    private OrderTableItem getSelectedOrder() {
        return ordersTable.getSelectionModel().getSelectedItem();
    }


    private UserTableItem getSelectedUser() {
        return usersTable.getSelectionModel().getSelectedItem();
    }

    private void confirmOrder() {
        getSelectedOrder().confirm();
        resetOrders();
    }

    private void triggerAutomobileControlButtons(boolean enabled) {
        for(Button button: automobileControlButtons()) {
            button.setDisable(!enabled);
        }
    }

    private void triggerOrderControlButtons(Boolean enabled) {
        if (getSelectedOrder().getConfirmed() == Order.CONFIRMED_STATUS) {
            confirmOrderButton.setDisable(true);
            return;
        }
        confirmOrderButton.setDisable(!enabled);
        exportCheckoutButton.setDisable(!enabled);
    }

    private void triggerUserDeleteButton(Boolean enabled) {
        if (getSelectedUser().getId() == ApplicationContext.getInstance().getCurrentUser().getId()) {
            deleteUserButton.setDisable(true);
            return;
        }
        deleteUserButton.setDisable(!enabled);
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
        automobile.setId(getSelectedAutomobile().getId());
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
            getSelectedAutomobile().getAutomobile(),
            getCurrentUser()
        );
    }

    private void deleteSelectedAutomobile() {
        getSelectedAutomobile().delete();
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
        AutomobileTableItem automobile = getSelectedAutomobile();

        brandField.setText(automobile.getBrand());
        nameField.setText(automobile.getName());
        serialNumberField.setText(automobile.getSerialNumber());
        productionYearField.setText(automobile.getProductionYear());
        costField.setText(automobile.getCost());
    }

    private void deleteUser() {
        User user = new User();
        user.setId(getSelectedUser().getId());
        user.delete();
        resetUsers();
        resetOrders();
    }

    private void setupCarsChart() {
        carsChart.setTitle("Статистика по автомобилям 2010-2018 годов");
        carsChart.setAnimated(false);
        carsChartXAxis.setLabel("Год производства");
        carsChartYAxis.setLabel("Количество автомобилей");
    }

    private void setDataToCarsChart() {
        XYChart.Series series = new LineChart.Series();
        series.setName("Автомобили в салоне");

        carsChartXAxis.setAutoRanging(true);

        for (String year: Arrays.asList("2010", "2011", "2012", "2013")) {
            series.getData().add(new XYChart.Data(year, carsManufacturedByYear(year)));
        }

        carsChart.getData().add(series);
    }

    private int carsManufacturedByYear(String year) {
        List<AutomobileTableItem> items = automobilesTable.getItems();
        return (int)items.stream().filter(item -> item.getProductionYear().equals(year)).count();
    }

    private void resetChart() {
        clearChart();
        setDataToCarsChart();
    }

    private void clearChart() {
        carsChart.getData().clear();
        setDataToCarsChart();
    }

    public void exportCheckout() {
        String filepath = System.getProperty("user.dir") + "/export/checkout.pdf";
        File file = new File(filepath);
        file.getParentFile().mkdirs();
        createCheckoutPdf(filepath);
    }

    public void createCheckoutPdf(String dest) {
        OrderTableItem order = getSelectedOrder();
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
            Document document = new Document(pdf);
            document.add(new Paragraph("Check"));
            document.add(new Paragraph("#################################"));
            document.add(new Paragraph("Avtomobil: " + order.getAutomobile()));
            document.add(new Paragraph("Stoimost: " + order.getCost() + "BYN"));
            document.add(new Paragraph("#################################"));
            document.add(new Paragraph("Pokupatel: " + order.getUser()));
            document.add(new Paragraph("Nomer kreditnoi karty: " + order.getPayment()));
            document.add(new Paragraph("Adres: " + order.getAddress()));
            document.add(new Paragraph("Telefon: " + order.getPhone()));
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
