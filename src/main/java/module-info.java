module org.example.crm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.crm to javafx.fxml;
    exports org.example.crm;
    exports org.example.crm.controllers;
    opens org.example.crm.controllers to javafx.fxml;
}