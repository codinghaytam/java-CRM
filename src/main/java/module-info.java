module org.example.crm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires jbcrypt;
    requires org.junit.jupiter.api;
    requires org.mockito;

    opens org.example.crm to javafx.fxml;
    opens org.example.crm.dao.impl to org.junit.platform.commons;  // <-- Open this package to JUnit
    exports org.example.crm;
    exports org.example.crm.controllers;
    opens org.example.crm.controllers to javafx.fxml;
}
