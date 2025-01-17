package org.jmc.template_javafx.controller.App;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.database.DeliveryManDAO;
import org.jmc.template_javafx.model.Delivery;
import org.jmc.template_javafx.model.Order;
import org.jmc.template_javafx.view.Start.NOTIFICATION_TYPE;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

// This class hasn't been applied CRUDControllerInterface yet but the structure are similar

public class DeliverymanController implements Initializable {
    public TableView<Delivery> tableView;
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

    public ObservableList<Delivery> observableList = FXCollections.observableArrayList();
    public ObservableList<Order> orderObservableList = FXCollections.observableArrayList();

    private DeliveryManDAO dao = new DeliveryManDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decor();
        setButtonAction();
        setData();
        populateData();
    }

    private void populateData(){
        tableView.setItems(observableList);
        observableList.setAll(dao.getAll());
        orders_TableView.setItems(orderObservableList);

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
                observableList.setAll(dao.getAll());
            }
            else{
                search();
            }
        });
    }

    private void setButtonAction(){
        saveCreate_btn.setOnAction(event -> saveCreateShipper());
        saveUpdate_btn.setOnAction(event -> saveUpdate());
        create_btn.setOnAction(event -> create());
        update_btn.setOnAction(event -> update());
        delete_btn.setOnAction(event -> delete());
        search_btn.setOnAction(event -> search());
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

    private void showInfor(Delivery user){
        id_input.setText(user.idPropertyProperty().get() + "");
        fullName_input.setText(user.namePropertyProperty().get());
        address_input.setText(user.addressPropertyProperty().get());
        phone_input.setText(user.phonePropertyProperty().get());

        id_input.setEditable(false);
        fullName_input.setEditable(false);
        address_input.setEditable(false);
        phone_input.setEditable(false);

        Set<Order> orders = user.ordersPropertyProperty().get();
        if(orders != null) orderObservableList.setAll(orders);
    }

    private void search() {
        String keyword = search_input.getText();
        if(keyword.isBlank()){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Please enter keyword to search");
        }
        observableList.setAll(dao.search(keyword));
    }

    private void delete() {

        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to delete this customer?")){
            return;
        }
        Delivery current = tableView.getSelectionModel().getSelectedItem();
        dao.delete(current);
        observableList.remove(current);

        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Delete deliveryman successfully");
    }

    private void update() {
        id_input.setEditable(false);
        fullName_input.setEditable(true);
        address_input.setEditable(true);
        phone_input.setEditable(true);

        saveUpdate_btn.setVisible(true);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.INFO, anchorPane, "Please fill in the form to update customer");
    }

    private void create() {
        id_input.clear();
        id_input.setText("AUTO GENERATE");
        fullName_input.clear();
        address_input.clear();
        phone_input.clear();
        saveCreate_btn.setVisible(true);

        id_input.setEditable(false);
        fullName_input.setEditable(true);
        address_input.setEditable(true);
        phone_input.setEditable(true);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.INFO, anchorPane, "Please fill in the form to create shipper");

    }

    private void saveUpdate() {
        saveUpdate_btn.setVisible(false);
        Delivery current = tableView.getSelectionModel().getSelectedItem();
        if(current == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.ERROR, anchorPane, "Please select a shipper to update");
            return;
        }
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to save update?")) return;

        current.setName(fullName_input.getText());
        current.setAddress(address_input.getText());
        current.setPhone(phone_input.getText());


        dao.update(current);
        observableList.setAll(dao.getAll());
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Update shipper successfully");
    }

    private void saveCreateShipper() {
        saveCreate_btn.setVisible(false);
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to create customer?")) return;

        String name = fullName_input.getText();
        String address = address_input.getText();
        String phone = phone_input.getText();

        Delivery current = new Delivery();
        current.setName(name);
        current.setAddress(address);
        current.setPhone(phone);

        dao.add(current);

        observableList.add(current);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Create shipper successfully");
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
