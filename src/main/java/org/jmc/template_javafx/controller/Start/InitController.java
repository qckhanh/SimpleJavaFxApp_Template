package org.jmc.template_javafx.controller.Start;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.util.ResourceBundle;

public class InitController implements Initializable {
    @FXML
    private BorderPane parent_BorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openLogin();
    }

    public void openRegister(){
        try{
//            parent_BorderPane.getStylesheets().add(FXSkins.getStylesheetURL());

            parent_BorderPane.setCenter(ViewCentral.getInstance().getStartViewFactory().getRegisterView());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openLogin(){
        try{
            parent_BorderPane.setCenter(ViewCentral.getInstance().getStartViewFactory().getLoginView());
        }
        catch (Exception e){
            System.out.println("Error loading login.fxml");
        }
    }

}
