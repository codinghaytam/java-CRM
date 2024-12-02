package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.crm.dao.impl.AgentCommercialDaoImpl;

import static org.example.crm.controllers.MainController.navigateTo;

public class AgentCommercialLoginController {
    @FXML
    private TextField cne;

    @FXML
    private TextField password;

    @FXML
    private Label messageLabel;

    @FXML
    private Button btn;
    @FXML
    private void login() {
        AgentCommercialDaoImpl agentCommercialDao = new AgentCommercialDaoImpl();
        if (agentCommercialDao.verifyLogin(cne.getText(), password.getText())){
            navigateTo("view/Agent/AgentCommertional.fxml", "Page Agent",btn);

        }else {
            messageLabel.setText("Invalid CNE or password");
        }
    }
}
