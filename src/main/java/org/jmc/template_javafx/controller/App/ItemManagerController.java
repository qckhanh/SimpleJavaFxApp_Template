package org.jmc.template_javafx.controller.App;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.database.ItemDAO;
import org.jmc.template_javafx.model.Item;
import org.jmc.template_javafx.view.Start.NOTIFICATION_TYPE;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ItemManagerController implements Initializable, CRUDControllerInterface {
    public AnchorPane anchorPane;
    public TextField search_input;
    public Button search_btn;
    public TableView<Item> tableView;
    public TextField id_input;
    public TextField itemName_input;
    public TextField price_input;
    public Button saveCreate_btn;
    public Button saveUpdate_btn;
    public Button create_btn;
    public Button update_btn;
    public Button delete_btn;

    private ItemDAO itemDAO = new ItemDAO();
    private ObservableList<Item> observableList = FXCollections.observableArrayList(itemDAO.getAll());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decor();
        setupData();
        insertData();
        setButtonActions();
    }



    @Override
    public void setButtonActions() {
        create_btn.setOnAction(e -> create());
        update_btn.setOnAction(e -> update());
        delete_btn.setOnAction(e -> delete());
        saveCreate_btn.setOnAction(e -> saveCreate());
        saveUpdate_btn.setOnAction(e -> saveUpdate());
        search_btn.setOnAction(e -> search());
        search_input.setOnAction(e -> search());

        saveUpdate_btn.setVisible(false);
        saveCreate_btn.setVisible(false);
    }
    @Override
    public void setupData() {
        tableView.getColumns().addAll(
                createColumn("ID", "id"),
                createColumn("Name", "name"),
                createColumn("Price", "price")
        );

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                view();
            }
        });


        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank()){
                observableList.setAll(itemDAO.getAll());
            }
            else{
                view();
            }
        });
    }

    @Override
    public void insertData() {
        tableView.setItems(observableList);
    }


    @Override
    public void view() {
        setEditable(false);

        saveCreate_btn.setVisible(false);
        saveUpdate_btn.setVisible(false);

        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Please select an item to view");
            return;
        }

        id_input.setText(String.valueOf(selectedItem.getId()));
        itemName_input.setText(selectedItem.getName());
        price_input.setText(String.valueOf(selectedItem.getPrice()));
    }

    @Override
    public void update() {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Please select an item to update");
            return;
        }

        setEditable(true);
        saveUpdate_btn.setVisible(true);
        id_input.setEditable(false);

    }

    public void create() {
        saveCreate_btn.setVisible(true);

        id_input.clear();
        itemName_input.clear();
        price_input.clear();

        id_input.setText("AUTO GENERATE");
        setEditable(true);
        id_input.setEditable(false);
    }

    @Override
    public void delete() {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Please select an item to delete");
            return;
        }
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to delete item?")) return;
        itemDAO.delete(selectedItem);
        observableList.remove(selectedItem);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Delete item successfully");
    }

    @Override
    public void search() {
        if(search_input.getText().isBlank()){
            ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Please enter a keyword to search");
            return;
        }
        observableList.setAll(itemDAO.search(search_input.getText()));
    }

    @Override
    public void saveUpdate() {
        saveUpdate_btn.setVisible(false);

        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to update item?")){
            setEditable(false);
            return;
        }
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();

        String name = itemName_input.getText();
        String price = price_input.getText();

        selectedItem.setName(name);
        selectedItem.setPrice(Double.parseDouble(price));

        itemDAO.update(selectedItem);
        observableList.setAll(itemDAO.getAll());

        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Update item successfully");

    }

    @Override
    public void saveCreate() {
        saveCreate_btn.setVisible(false);
        if(!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure you want to create item?")){
            setEditable(false);
            price_input.clear();
            itemName_input.clear();
            return;
        }

        String name = itemName_input.getText();
        String price = price_input.getText();
        Item item = new Item();
        item.setName(name);
        item.setPrice(Double.parseDouble(price));
        itemDAO.add(item);
        observableList.add(item);
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Create item successfully");

    }

    @Override
    public void decor() {
        UIDecorator.setSuccessButton(create_btn, UIDecorator.ADD(), null);
        UIDecorator.setNormalButton(update_btn, UIDecorator.EDIT(), null);
        UIDecorator.setDangerButton(delete_btn, UIDecorator.DELETE(), null);
        UIDecorator.setNormalButton(saveCreate_btn, UIDecorator.SEND(), null);
        UIDecorator.setNormalButton(saveUpdate_btn, UIDecorator.SEND(), null);
        UIDecorator.setNormalButton(search_btn, UIDecorator.SEARCH(), null);
    }

    private void setEditable(boolean status){
        id_input.setEditable(status);
        itemName_input.setEditable(status);
        price_input.setEditable(status);
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
