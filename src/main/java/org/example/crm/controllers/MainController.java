package org.example.crm.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;

import java.io.IOException;

public class MainController {
    @FXML
    private Button adminLoginButton;

    @FXML
    private Button agentLoginButton;


    @FXML
    private void handleAdminLogin(ActionEvent event) {
        navigateTo("view/Supervisor/Login-view.fxml", "Admin Login" , adminLoginButton);
    }

    @FXML
    private void handleAgentLogin(ActionEvent event) {
        navigateTo("view/Supervisor/AgentLogin-view.fxml", "Agent Login" , agentLoginButton);
    }

    public static void navigateTo(String fxmlFilePath, String title , Button btn) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFilePath));
            Stage window = (Stage) btn.getScene().getWindow(); // Use messageLabel instead of btn
            window.setTitle(title);
            window.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
