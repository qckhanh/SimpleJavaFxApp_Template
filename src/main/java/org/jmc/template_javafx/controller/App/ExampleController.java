package org.jmc.template_javafx.controller.App;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ExampleController implements CRUDControllerInterface, Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonActions();
        decor();
        setupData();
        insertData();
    }

    @Override
    public void setButtonActions() {
        //button.setOnAction(e->doSomething());
    }

    @Override
    public void decor() {
        // UIDecorator.setNormalButton(customerManager_btn, UIDecorator.USER(), "Customer Manager");
    }

    @Override
    public void setupData() {
        //tableView.getColumn().addAll()...
        //comboBox.setCellFactory....
    }

    @Override
    public void insertData() {
        // observableList.setAll(new CustomerDAO().getAll());
        // tableView.setItems(observableList);

    }

    @Override
    public void create() {

    }

    @Override
    public void view() {
        //show the detail
    }

    @Override
    public void update() {
        //update the object
    }

    @Override
    public void delete() {
        //delete the object
    }

    @Override
    public void search() {
        //search the object
    }

    @Override
    public void saveUpdate() {
        // CustomerDAO.update(customer);

    }

    @Override
    public void saveCreate() {
        // CustomerDAO.create(customer);

    }
}
