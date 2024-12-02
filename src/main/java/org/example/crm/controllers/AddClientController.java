package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.crm.dao.impl.ClientDaoImpl;
import org.example.crm.dao.impl.DemandeDaoImpl;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Client;
import org.example.crm.models.Demande;
import org.example.crm.models.Status;
import org.example.crm.util.CurrentUser;

import java.util.UUID;

public class AddClientController {
    @FXML
    private TextField ClientName;

    @FXML
    private TextField ClientHeadquarters;

    @FXML
    private TextField ClientPhone;

    @FXML
    private TextField ClientEmail;

    private void AddClient() {
        ClientDaoImpl clientDao = new ClientDaoImpl();
        DemandeDaoImpl demandDao = new DemandeDaoImpl();
        String ClientName = this.ClientName.getText();
        String ClientHeadquarters = this.ClientHeadquarters.getText();
        String ClientPhone = this.ClientPhone.getText();
        String ClientEmail = this.ClientEmail.getText();
        Client client = new Client(UUID.randomUUID().toString(),ClientName, ClientHeadquarters, ClientPhone, ClientEmail);
        demandDao.addDemande(new Demande(client,Status.enAttente),new AgentCommercial("test","","","",""));
        clientDao.addClient(client);

    }


}
