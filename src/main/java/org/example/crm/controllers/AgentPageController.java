package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AgentPageController {

    @FXML
    private Button createLeadBtn;

    @FXML
    private Button showLeadsBtn;

    @FXML
    private Button deleteLeadsBtn;

    // Méthode pour créer un Lead
    @FXML
    private void createLead() {
        MainController.navigateTo("view/Agent/AddLead-view.fxml", "Create Lead", createLeadBtn);
    }

    // Méthode pour afficher les Leads
    @FXML
    private void showLeads() {
        MainController.navigateTo("view/Agent/ShowLeads-view.fxml", "Show Leads", showLeadsBtn);
    }
}
