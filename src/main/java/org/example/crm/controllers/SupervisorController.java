package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.crm.dao.SupervisorDao;
import org.example.crm.dao.impl.AgentCommercialDaoImpl;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.models.AgentCommercial;
import org.example.crm.util.CurrentUser;

public class SupervisorController {
    @FXML
    private TextField cneField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button addButton;

    @FXML
    private Label messageLabel;

    private final SupervisorDaoImpl SupervisorDaoImpl = new SupervisorDaoImpl();


    @FXML
    private void handleAddAgent(){
        String cne = cneField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String password = passwordField.getText();
        String Supervisor_id = CurrentUser.getLoggedInAdmin();

        if(cne.isEmpty() || nom.isEmpty() || prenom.isEmpty() || password.isEmpty()) {
            messageLabel.setText("One or many fields are empty!");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        AgentCommercial agent = new AgentCommercial(cne, nom, prenom, password, Supervisor_id);

        if (SupervisorDaoImpl.addAgent(agent)) {
            messageLabel.setText("Agent added successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } else {
            messageLabel.setText("Failed to add agent. Try again.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }




    }

    private void clearFields() {
        cneField.clear();
        nomField.clear();
        prenomField.clear();
        passwordField.clear();
    }

}
