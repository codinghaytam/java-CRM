package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.crm.dao.impl.AgentCommercialDaoImpl;
import org.example.crm.util.CurrentUser;
import java.io.IOException;

public class AgentLoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button btn;

    @FXML
    private void handleLogin() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();
        
        // Instancier la DAO de l'agent commercial
        AgentCommercialDaoImpl agentDao = new AgentCommercialDaoImpl();
        
        // Vérifier les identifiants
        if (agentDao.verifyLogin(enteredUsername, enteredPassword)) {
            CurrentUser.setLoggedInCommercial(enteredUsername);  // Set le commercial connecté
            MainController.navigateTo("view/Agent/AgentPage-view.fxml", "Agent Dashboard", btn);
        } else {
            messageLabel.setText("Invalid Username or Password");
        }
    }
}
