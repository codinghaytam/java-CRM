package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.example.crm.models.Lead;
import org.example.crm.util.CurrentUser;

import java.util.UUID;

import org.example.crm.dao.impl.AgentCommercialDaoImpl;
import org.example.crm.dao.impl.LeadDaoImpl;

public class AddLeadController {

    @FXML
    private TextField companyNameField;

    @FXML
    private TextField headquartersField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Button submitButton;

    @FXML
    private void handleAddLead() {
        String entrepriseId = UUID.randomUUID().toString();
        String companyName = companyNameField.getText();
        String headquarters = headquartersField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String agent_CNE = CurrentUser.getLoggedInCommercial();

        Lead leads = new Lead(entrepriseId,companyName, headquarters, phone, email, agent_CNE); // `entrepriseId` est généré dans la BDD

        LeadDaoImpl dao = new LeadDaoImpl();
        if (dao.ajoutLead(leads)) {
            System.out.println("Lead added successfully.");

        } else {
            System.out.println("Failed to add lead.");
        }
    }
}
