package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManageAgentsController {

    @FXML
    private Button createAgentBtn;

    @FXML
    private Button showAgentsBtn;


    @FXML
    private void createAgent() {
        MainController.navigateTo("view/Supervisor/AddAgent-view.fxml", "Create Agent",true,createAgentBtn);
    }

    @FXML
    private void showAgents() {
        MainController.navigateTo("view/Supervisor/showAgents-view.fxml", "Show Agents",true,showAgentsBtn);
    }


}

