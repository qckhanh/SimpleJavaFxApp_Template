module org.jmc.template_javafx {

    //please keep it as it is
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
    requires org.slf4j;
    requires com.fasterxml.classmate;
    requires java.transaction.xa;
    requires org.postgresql.jdbc;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires atlantafx.base;
    requires org.kordamp.ikonli.material2;
    requires java.management;
    requires java.desktop;
    requires jdk.jfr;
    requires org.kordamp.ikonli.fontawesome5;
    requires net.synedra.validatorfx;
    requires ant;
/*
    OPEN
*/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /*
        MODEL
    */
    opens org.jmc.template_javafx.model to javafx.fxml;
    // when a class is not directly in the /Model folder,
    // it should be opened like this
    //opens org.jmc.template_javafx.model.Persons to javafx.fxml;

    /*
        CONTROLLER
    */
    opens org.jmc.template_javafx.controller.Start to javafx.fxml;
    opens org.jmc.template_javafx.controller.App to javafx.fxml;

/*
    EXPORTS
*/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    exports org.jmc.template_javafx;
    exports org.jmc.template_javafx.model;
    exports org.jmc.template_javafx.view;


    /*
        MODEL
     */
    /*
        When a class is not directly in the /Model folder, it should be exported like this
        exports org.jmc.template_javafx.model.Persons;
    */


    /*
        CONTROLLER
     */
    exports org.jmc.template_javafx.controller.App;
    exports org.jmc.template_javafx.controller.Start;



    /*
        VIEW
     */
    exports org.jmc.template_javafx.view.Start;
    exports org.jmc.template_javafx.view.App;

}