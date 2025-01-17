package org.jmc.template_javafx.controller.Start;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.view.Start.NOTIFICATION_TYPE;
import org.jmc.template_javafx.view.ViewCentral;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button signIn_btn;
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signIn_btn.setOnAction(e->signIn());
        UIDecorator.setSuccessButton(signIn_btn, UIDecorator.LOG_IN(), "Open Application");
        ViewCentral.getInstance().getStartViewFactory().pushNotification(NOTIFICATION_TYPE.SUCCESS, anchorPane, "Data initialized successfully");

    }

    private void signIn() {
        ViewCentral.getInstance().getApplicationViewFactory().startApplicationView();
        Stage currentStage = (Stage) signIn_btn.getScene().getWindow();
        ViewCentral.getInstance().getStartViewFactory().closeStage(currentStage);
    }
}
