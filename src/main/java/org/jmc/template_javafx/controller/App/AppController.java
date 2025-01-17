package org.jmc.template_javafx.controller.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.jmc.template_javafx.view.App.MENU_OPTION;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    BorderPane borderPane;

    public AppController() {
        ViewCentral.getInstance().getApplicationViewFactory().selectedMenuOptionProperty().addListener(((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case CUSTOMER_MANAGER -> borderPane.setCenter(ViewCentral.getInstance().getApplicationViewFactory().getCustomerManagerView());
                case ITEM_MANAGER -> borderPane.setCenter(ViewCentral.getInstance().getApplicationViewFactory().getItemManagerView());
                case SHIPPER_MANAGER -> borderPane.setCenter(ViewCentral.getInstance().getApplicationViewFactory().getDeliverymanManager());
                case ORDER_MANAGER -> borderPane.setCenter(ViewCentral.getInstance().getApplicationViewFactory().getOrderManagerView());
                //add new case if there is new menu option
            }
        }));
        ViewCentral.getInstance().getApplicationViewFactory().setSelectedMenuOption(MENU_OPTION.OBJECT1_MANAGER);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
