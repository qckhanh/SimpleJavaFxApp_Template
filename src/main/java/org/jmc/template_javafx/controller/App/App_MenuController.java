package org.jmc.template_javafx.controller.App;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.view.App.MENU_OPTION;
import org.jmc.template_javafx.view.ViewCentral;


import java.net.URL;
import java.util.ResourceBundle;

public class App_MenuController implements Initializable {
    public Button exit_btn;
    public Button customerManager_btn;
    public Button itemManager_btn;
    public Button shipperManager_btn;
    public Button orderManager_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonActions();
        decor();
    }

    public void setButtonActions() {
        customerManager_btn.setOnAction(e-> openCusterManagerView());
        itemManager_btn.setOnAction(e-> openItemManagerView());
        shipperManager_btn.setOnAction(e-> openShipperManagerView());
        orderManager_btn.setOnAction(e-> openOrderManagerView());

        exit_btn.setOnAction(e->exiApp());
    }

    public void decor() {
        UIDecorator.setNormalButton(customerManager_btn, UIDecorator.USER(), "Customer Manager");
        UIDecorator.setNormalButton(itemManager_btn, UIDecorator.ITEM(), "Item Manager");
        UIDecorator.setNormalButton(shipperManager_btn, UIDecorator.SHIPPER(), "Shipper Manager");
        UIDecorator.setNormalButton(orderManager_btn, UIDecorator.ORDER(), "Order Manager");
        UIDecorator.setDangerButton(exit_btn, UIDecorator.LOG_OUT(), "Exit");
    }


    private void openOrderManagerView() {
        ViewCentral.getInstance().getApplicationViewFactory().setSelectedMenuOption(MENU_OPTION.ORDER_MANAGER);
    }

    private void openShipperManagerView() {
        ViewCentral.getInstance().getApplicationViewFactory().setSelectedMenuOption(MENU_OPTION.SHIPPER_MANAGER);
    }

    private void openItemManagerView() {
        ViewCentral.getInstance().getApplicationViewFactory().setSelectedMenuOption(MENU_OPTION.ITEM_MANAGER);
    }

    private void openCusterManagerView() {
        ViewCentral.getInstance().getApplicationViewFactory().setSelectedMenuOption(MENU_OPTION.CUSTOMER_MANAGER);
    }

    private void exiApp() {
        ViewCentral.getInstance().getApplicationViewFactory().setSelectedMenuOption(MENU_OPTION.OBJECT1_MANAGER);
        ViewCentral.getInstance().getApplicationViewFactory().resetView();
        ViewCentral.getInstance().getStartViewFactory().logOut(exit_btn);

    }



}
