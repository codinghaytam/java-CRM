package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminHomeController {

    @FXML
    private Button createAgentBtn;

    @FXML
    private Button showAgentBtn;

    @FXML
    private Button deleteAgentBtn;

    @FXML
    private void createAgent() {
        MainController.navigateTo("view/Supervisor/AddAgent-view.fxml", "Create Agent",createAgentBtn);
    }

    @FXML
    private void showAgents() {
        MainController.navigateTo("ShowAgents-view.fxml", "Show Agents",showAgentBtn);
    }

    @FXML
    private void deleteAgent() {
        MainController.navigateTo("DeleteAgents-view.fxml", "Delete Agents",deleteAgentBtn);
    }


}

