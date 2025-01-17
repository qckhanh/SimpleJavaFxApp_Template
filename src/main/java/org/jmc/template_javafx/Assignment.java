package org.jmc.template_javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jmc.template_javafx.Helper.DateUtils;
import org.jmc.template_javafx.Helper.UIDecorator;
import org.jmc.template_javafx.database.CustomerDAO;
import org.jmc.template_javafx.database.Database;
import org.jmc.template_javafx.database.OrderDAO;
import org.jmc.template_javafx.model.Customer;
import org.jmc.template_javafx.model.Order;
import org.jmc.template_javafx.view.ViewCentral;

import java.io.IOException;
import java.text.ParseException;


public class Assignment extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        Database.getInstance();
        UIDecorator.setApplicationTheme();
        ViewCentral.getInstance().getStartViewFactory().start();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        Database.getInstance().saveData();
        System.out.println("Data saved");
    }

    public static void main(String[] args) {
        launch();
    }
}