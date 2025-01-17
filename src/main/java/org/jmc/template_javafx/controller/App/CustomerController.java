package org.jmc.template_javafx.controller.App;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.database.CustomerDAO;
import org.jmc.template_javafx.model.Customer;
import org.jmc.template_javafx.model.Order;
import org.jmc.template_javafx.view.Start.NOTIFICATION_TYPE;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

// This class hasn't been applied CRUDControllerInterface yet but the structure are similar
public class CustomerController implements Initializable {
    public TableView<Customer> tableView;
    public TextField id_input;
    public TextField fullName_input;
    public TextField address_input;
    public TextField phone_input;
    public TableView<Order> orders_TableView;
    public Button saveCreate_btn;
    public Button saveUpdate_btn;
    public Button create_btn;
    public Button update_btn;
    public Button delete_btn;
    public TextField search_input;
    public Button search_btn;
    public AnchorPane anchorPane;

    public ObservableList<Customer> observableList = FXCollections.observableArrayList();
    public ObservableList<Order> orderObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decor();
        setButtonAction();
        setData();
        populateData();
    }

    private void populateData(){
        tableView.setItems(observableList);
        orders_TableView.setItems(orderObservableList);
        observableList.setAll(new CustomerDAO().getAll());

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInfor(newValue);
            }
        });
    }

    private void setData() {
        tableView.getColumns().addAll(
                createColumn("ID", "id"),
                createColumn("Full Name", "name"),
//                createColumn("Address", "address"),
                createColumn("Phone", "phone")
        );

        orders_TableView.getColumns().addAll(
                createColumn("ID", "id"),
                createColumn("Name", "item", order -> order.itemPropertyProperty().get().namePropertyProperty().get()),
                createColumn("Order Date", "createdDate"),
                createColumn("Price", "totalPrice")
        );

        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                observableList.setAll(new CustomerDAO().getAll());
            }
            else{
                searchCustomer();
            }
        });
    }

    private void setButtonAction(){
        saveCreate_btn.setOnAction(event -> saveCreateCustomer());
        saveUpdate_btn.setOnAction(event -> saveUpdateCustomer());
        create_btn.setOnAction(event -> createCustomer());
        update_btn.setOnAction(event -> updateCustomer());
        delete_btn.setOnAction(event -> deleteCustomer());
        search_btn.setOnAction(event -> searchCustomer());
        saveCreate_btn.setVisible(false);
        saveUpdate_btn.setVisible(false);
    }

    private void decor(){
        UIDecorator.setNormalButton(saveCreate_btn, UIDecorator.SEND(), "Save create");
        UIDecorator.setNormalButton(saveUpdate_btn, UIDecorator.SEND(), "Save update");
        UIDecorator.setSuccessButton(create_btn, UIDecorator.ADD(), null);
        UIDecorator.setNormalButton(update_btn, UIDecorator.EDIT(), null);
        UIDecorator.setDangerButton(delete_btn, UIDecorator.DELETE(), null);
        UIDecorator.setNormalButton(search_btn, UIDecorator.SEARCH(), null);
    }

    private void showInfor(Customer customer){
        id_input.setText(customer.idPropertyProperty().get() + "");
        fullName_input.setText(customer.namePropertyProperty().get());
        address_input.setText(customer.addressPropertyProperty().get());
        phone_input.setText(customer.phonePropertyProperty().get());

        id_input.setEditable(false);
        fullName_input.setEditable(false);
        address_input.setEditable(false);
        phone_input.setEditable(false);
        Set<Order> orders = customer.ordersPropertyProperty().get();
        if(orders != null) orderObservableList.setAll(customer.ordersPropertyProperty().get());
    }

    private void searchCustomer() {
        String keyword = search_input.getText();
        if(keyword.isBlank()){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Please enter keyword to search");
        }
        CustomerDAO customerDAO = new CustomerDAO();
        observableList.setAll(customerDAO.search(keyword));
    }

    private void deleteCustomer() {

        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to delete this customer?")){
            return;
        }
        Customer customer = tableView.getSelectionModel().getSelectedItem();
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.delete(customer);
        observableList.remove(customer);

        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Delete customer successfully");
    }

    private void updateCustomer() {
        id_input.setEditable(false);
        fullName_input.setEditable(true);
        address_input.setEditable(true);
        phone_input.setEditable(true);

        saveUpdate_btn.setVisible(true);
        saveCreate_btn.setVisible(false);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.INFO, anchorPane, "Please fill in the form to update customer");
    }

    private void createCustomer() {
        id_input.clear();
        id_input.setText("AUTO GENERATE");
        fullName_input.clear();
        address_input.clear();
        phone_input.clear();
        saveUpdate_btn.setVisible(false);
        saveCreate_btn.setVisible(true);

        id_input.setEditable(false);
        fullName_input.setEditable(true);
        address_input.setEditable(true);
        phone_input.setEditable(true);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.INFO, anchorPane, "Please fill in the form to create customer");
    }

    private void saveUpdateCustomer() {
        saveUpdate_btn.setVisible(false);
        Customer customer = tableView.getSelectionModel().getSelectedItem();
        if(customer == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.ERROR, anchorPane, "Please select a customer to update");
            return;
        }
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to save update?")) return;

        customer.setName(fullName_input.getText());
        customer.setAddress(address_input.getText());
        customer.setPhone(phone_input.getText());


        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.update(customer);
        observableList.setAll(customerDAO.getAll());
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Update customer successfully");
    }

    private void saveCreateCustomer() {
        saveCreate_btn.setVisible(false);
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to create customer?")) return;

        String name = fullName_input.getText();
        String address = address_input.getText();
        String phone = phone_input.getText();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.add(customer);

        observableList.add(customer);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Create customer successfully");
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
