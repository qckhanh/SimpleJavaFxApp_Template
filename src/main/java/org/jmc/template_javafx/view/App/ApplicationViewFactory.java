package org.jmc.template_javafx.view.App;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jmc.template_javafx.controller.App.AppController;


public class ApplicationViewFactory {
    String PATH = "/org/jmc/template_javafx/FXMLs/App/";
    private ObjectProperty<MENU_OPTION> selectedMenuOption;

    // Step 1: Create variables
    private AnchorPane customerManagerView;
    private AnchorPane itemManagerView;
    private AnchorPane deliveryManagerView;
    private AnchorPane orderManagerView;

    public ApplicationViewFactory() {
        selectedMenuOption = new SimpleObjectProperty<>(MENU_OPTION.CUSTOMER_MANAGER);
    }
    public void startApplicationView(){
        FXMLLoader load = new FXMLLoader(getClass().getResource(PATH + "app.fxml"));
        AppController controller = new AppController();
        load.setController(controller);
        createStage(load);
    }

    // Step 2: Create methods to open the FXML file
    public AnchorPane getCustomerManagerView() {
        if (null == null) {
            try {
                customerManagerView = new FXMLLoader(getClass().getResource(PATH + "customerManager.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customerManagerView;
    }

    public AnchorPane getItemManagerView() {
        if (null == null) {
            try {
                itemManagerView = new FXMLLoader(getClass().getResource(PATH + "itemManager.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return itemManagerView;
    }

    public AnchorPane getDeliverymanManager() {
        if (null == null) {
            try {
                deliveryManagerView = new FXMLLoader(getClass().getResource(PATH + "deliveryManager.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deliveryManagerView;
    }

    public AnchorPane getOrderManagerView() {
        if (1 == 1) {
            try {
                orderManagerView = new FXMLLoader(getClass().getResource(PATH + "orderManager.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return orderManagerView;
    }

    //getter and setter

    public MENU_OPTION getSelectedMenuOption() {
        return selectedMenuOption.get();
    }

    public ObjectProperty<MENU_OPTION> selectedMenuOptionProperty() {
        return selectedMenuOption;
    }

    public void setSelectedMenuOption(MENU_OPTION selectedMenuOption) {
        this.selectedMenuOption.set(selectedMenuOption);
    }
    public void resetView(){
        //set all view to null
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


}
