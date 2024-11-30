package org.example.crm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.crm.dao.impl.AgentCommercialDaoImpl;
import org.example.crm.dao.impl.ClientDaoImpl;
import org.example.crm.models.Client;
import org.example.crm.util.DatabaseInit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        //DatabaseInit.Initialize_Database();
        AgentCommercialDaoImpl Agentdao = new AgentCommercialDaoImpl();

        launch();
    }
}