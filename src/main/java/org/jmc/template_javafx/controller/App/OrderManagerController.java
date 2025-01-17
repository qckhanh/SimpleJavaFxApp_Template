package org.jmc.template_javafx.controller.App;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.synedra.validatorfx.Validator;
import org.jmc.template_javafx.Helper.DateUtils;
import org.jmc.template_javafx.Helper.InputValidator;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.database.CustomerDAO;
import org.jmc.template_javafx.database.DeliveryManDAO;
import org.jmc.template_javafx.database.ItemDAO;
import org.jmc.template_javafx.database.OrderDAO;
import org.jmc.template_javafx.model.Customer;
import org.jmc.template_javafx.model.Delivery;
import org.jmc.template_javafx.model.Item;
import org.jmc.template_javafx.model.Order;
import org.jmc.template_javafx.view.Start.NOTIFICATION_TYPE;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Function;

public class OrderManagerController implements Initializable, CRUDControllerInterface {
    public AnchorPane anchorPane;
    public TextField search_input;
    public TableView<Order> tableView;
    public TextField id_input;
    public DatePicker createdDate_comboBox;
    public ComboBox<Item> item_comboBox;
    public ComboBox<Customer> customer_comboBox;
    public ComboBox<Delivery> deliveryman_comboBox;
    public Button saveCreate_btn;
    public Button saveUpdate_btn;
    public Button create_btn;
    public Button update_btn;
    public Button delete_btn;
    public Button search_btn;
    public TextField price_input;
    private OrderDAO orderDAO = new OrderDAO();
    private ItemDAO itemDAO = new ItemDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private DeliveryManDAO deliveryManDAO = new DeliveryManDAO();

    private ObservableList<Order> observableList = FXCollections.observableArrayList();
    private ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    private ObservableList<Delivery> deliveryObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decor();
        setButtonActions();
        insertData();
        setupData();
    }

    public void insertData(){
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                view();
            }
        });

        observableList.setAll(orderDAO.getAll());
        itemObservableList.setAll(itemDAO.getAll());
        customerObservableList.setAll(customerDAO.getAll());
        deliveryObservableList.setAll(deliveryManDAO.getAll());

        customer_comboBox.setItems(customerObservableList);
        item_comboBox.setItems(itemObservableList);
        deliveryman_comboBox.setItems(deliveryObservableList);
        tableView.setItems(observableList);

    }

    public void view() {
        Order newValue = tableView.getSelectionModel().getSelectedItem();
        setDisable(false);
        id_input.setText(newValue.getId() + "");
        createdDate_comboBox.setValue(newValue.getCreatedDate());
        item_comboBox.setValue(newValue.getItem());
        customer_comboBox.setValue(newValue.getCustomer());
        deliveryman_comboBox.setValue(newValue.getDelivery());
        price_input.setText(newValue.getTotalPrice() + "");


    }

    public void setupData(){
        tableView.getColumns().addAll(
                createColumn("ID", "id"),
                createColumn("Item", "item", order -> order.itemPropertyProperty().get().getName()),
                createColumn("Order Date", "createdDate", order ->
                {
                    return DateUtils.formatDate(order.createdDatePropertyProperty().get());
                })
        );

        customer_comboBox.setCellFactory(c -> new ListCell<>(){
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    // this is the way it display
                    setText(item.getId() + " - " + item.namePropertyProperty().get());
                }
            }
        });

        //set is the way it display when it is selected
        customer_comboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getId() + " - " + item.namePropertyProperty().get());
                }
            }
        });

        deliveryman_comboBox.setCellFactory(c -> new ListCell<>(){
            @Override
            protected void updateItem(Delivery item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getId() + " - " + item.namePropertyProperty().get());
                }
            }
        });

        deliveryman_comboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Delivery item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getId() + " - " + item.namePropertyProperty().get());
                }
            }
        });

        item_comboBox.setCellFactory(c -> new ListCell<>(){
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getId() + " - " + item.namePropertyProperty().get());
                }
            }
        });

        item_comboBox.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getId() + " - " + item.namePropertyProperty().get());
                }
            }
        });

        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank()){
                observableList.setAll(orderDAO.getAll());
            }
            else{
                search();
            }
        });
    }

    public void setButtonActions(){
        saveCreate_btn.setOnAction(e -> saveCreate());
        saveUpdate_btn.setOnAction(e -> saveUpdate());
        create_btn.setOnAction(e -> create());
        update_btn.setOnAction(e -> update());
        delete_btn.setOnAction(e -> delete());
        search_btn.setOnAction(e -> search());

        saveCreate_btn.setVisible(false);
        saveUpdate_btn.setVisible(false);

    }

    public void saveCreate() {
        saveCreate_btn.setVisible(false);
        //input validation
        Validator validator = new Validator();
        buildValidate_UPDATE(validator);

        //if input is invalid
        if(!validator.validate()){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.ERROR, anchorPane, "Please check your input");

            return;
        }
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to create this order?")){
            item_comboBox.setValue(null);
            customer_comboBox.setValue(null);
            deliveryman_comboBox.setValue(null);
            price_input.clear();

            return;
        }
        Order order = new Order();
        order.setCreatedDate(createdDate_comboBox.getValue());
        order.setItem(item_comboBox.getValue());
        //add customer
        Customer customer = customer_comboBox.getValue();
        customer.addOrder(order);
        order.setCustomer(customer);
        order.setTotalPrice(Double.parseDouble(price_input.getText()));

        //add delivery
        Delivery delivery = deliveryman_comboBox.getValue();
        delivery.addOrder(order);
        order.setDelivery(delivery);

        System.out.println(orderDAO.add(order));

        observableList.add(order);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Order created successfully");
    }

    public void saveUpdate() {
        saveUpdate_btn.setVisible(false);
        Order current = tableView.getSelectionModel().getSelectedItem();
        if(current == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.ERROR, anchorPane, "Please select an order to update");
            return;
        }
        Validator validator = new Validator();
        buildValidate_UPDATE(validator);
        if(!validator.validate()){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.ERROR, anchorPane, "Please check your input");
            return;
        }
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to update this order?")){
            view();
            return;
        }

        //update customer
        Customer oldCustomer = current.getCustomer();
        oldCustomer.removeOrder(current);
        Customer newCustomer = customer_comboBox.getSelectionModel().getSelectedItem();
        newCustomer.addOrder(current);
        current.setCustomer(newCustomer);

        //update delivery
        Delivery oldDelivery = current.getDelivery();
        oldDelivery.removeOrder(current);
        Delivery newDelivery = deliveryman_comboBox.getSelectionModel().getSelectedItem();
        newDelivery.addOrder(current);
        current.setDelivery(newDelivery);

        current.setCreatedDate(createdDate_comboBox.getValue());
        current.setItem(item_comboBox.getValue());
        current.setTotalPrice(Double.parseDouble(price_input.getText()));

        orderDAO.update(current);
        observableList.setAll(orderDAO.getAll());

        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Update order successfully");
        saveUpdate_btn.setVisible(false);
        setDisable(false);
    }

    public void search() {
        if(!search_input.getText().isBlank()){
            observableList.setAll(orderDAO.search(search_input.getText()));
        }
    }

    public void delete() {
        Order current = tableView.getSelectionModel().getSelectedItem();
        if(current == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.ERROR, anchorPane, "Please select an order to delete");
            return;
        }
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to delete this order?")){
            return;
        }

        Customer customer = current.getCustomer();
        customer.removeOrder(current);

        Delivery delivery = current.getDelivery();
        delivery.removeOrder(current);

        orderDAO.delete(current);
        observableList.remove(current);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Delete order successfully");

    }

    public void update() {
        saveUpdate_btn.setVisible(true);
        setDisable(true);
    }

    public void create() {
        saveCreate_btn.setVisible(true);

        id_input.clear();
        createdDate_comboBox.setValue(null);
        item_comboBox.setValue(null);
        customer_comboBox.setValue(null);
        deliveryman_comboBox.setValue(null);

        id_input.setText("AUTO GERNERATE");
        setDisable(true);
    }

    public void decor(){
        UIDecorator.setNormalButton(saveCreate_btn, UIDecorator.SEND(), "Save create");
        UIDecorator.setNormalButton(saveUpdate_btn, UIDecorator.SEND(), "Save update");
        UIDecorator.setSuccessButton(create_btn, UIDecorator.ADD(), null);
        UIDecorator.setNormalButton(update_btn, UIDecorator.EDIT(), null);
        UIDecorator.setDangerButton(delete_btn, UIDecorator.DELETE(), null);
        UIDecorator.setNormalButton(search_btn, UIDecorator.SEARCH(), null);
    }

    private void setDisable(boolean status){
        id_input.setEditable(false);
        createdDate_comboBox.setDisable(!status);
        item_comboBox.setDisable(!status);
        customer_comboBox.setDisable(!status);
        deliveryman_comboBox.setDisable(!status);
        price_input.setEditable(status);

    }

    private void buildValidate_UPDATE(Validator validator){
        validator.createCheck()
                .dependsOn("price", price_input.textProperty())
                .withMethod(context -> {
                    String input = context.get("price");
                    if(!InputValidator.isValidDouble(input)){
                        context.error("Price must be a number");
                    }
                    if(InputValidator.isValidDouble(input)){
                        double price = Double.parseDouble(input);
                        if(price <= 0) context.error("Price must be greater than 0");
                    }

                })
                .decorates(price_input)
                .immediateClear();

        validator.createCheck()
                .dependsOn("item", item_comboBox.getSelectionModel().selectedItemProperty())
                .withMethod(context -> {
                    Item item = context.get("item");
                    if(item == null){
                        context.error("Please select an item");
                    }
                })
                .decorates(item_comboBox)
                .immediateClear();

        validator.createCheck()
                .dependsOn("customer", customer_comboBox.getSelectionModel().selectedItemProperty())
                .withMethod(context -> {
                    Customer customer = context.get("customer");
                    if(customer == null){
                        context.error("Please select a customer");
                    }
                })
                .decorates(customer_comboBox)
                .immediateClear();

        validator.createCheck()
                .dependsOn("delivery", deliveryman_comboBox.getSelectionModel().selectedItemProperty())
                .withMethod(context -> {
                    Delivery delivery = context.get("delivery");
                    if (delivery == null) {
                        context.error("Please enter a delivery");
                    }
                })
                .decorates(deliveryman_comboBox)
                .immediateClear();

        validator.createCheck()
                .dependsOn("createdDate", createdDate_comboBox.valueProperty())
                .withMethod(context -> {
                    LocalDate date = context.get("createdDate");
                    if(!InputValidator.isValidDateFormat(date)){
                        context.error("Please select a date");
                    }
                })
                .decorates(createdDate_comboBox)
                .immediateClear();


    }

    private <S, T> TableColumn<S, T> createColumn(String columnName, String propertyName) {
        TableColumn<S, T> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }

    private <S, T> TableColumn<S, T> createColumn(String columnName, String propertyName, Function<S, T> extractor) {
        TableColumn<S, T> column = new TableColumn<>(columnName);

        column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(extractor.apply(cellData.getValue())));
        column.setCellFactory(col -> new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle(""); // Reset cell style for empty cells
                    setOnMouseClicked(null); // Remove any click listeners when the cell is empty
                } else {
                    setText(item.toString());
                }
            }
        });

        return column;
    }
}
