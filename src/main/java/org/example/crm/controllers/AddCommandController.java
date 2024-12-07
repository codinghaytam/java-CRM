package org.example.crm.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.crm.dao.impl.*;
import org.example.crm.models.Client;
import org.example.crm.models.Commande;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.models.Produit;
import org.example.crm.util.CurrentUser;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AddCommandController implements Initializable {

    private List<Produit> produits=new ArrayList<>();
    private List<Produit> DisplayProduits=new ArrayList<>();
    private List<Client> DisplayClients=new ArrayList<>();

    @FXML
    private ComboBox<String> produitComboBox;
    @FXML
    private ComboBox<String> clientComboBox;
    @FXML
    private VBox ShopingCarte;
    @FXML
    private TextField quantiteTextField;

    private Commande commande;


    @FXML
    private void AddProduitToController() {
        Produit myproduit ;
        Client client;
        List<Client> clients;
        LoyaltyCard loyaltyCard;
        try {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            LoyaltyCardDaoImpl loyaltyCardDao = new LoyaltyCardDaoImpl();
            clients = clientDao.selectAll();
            client = clients.stream().filter(cl-> cl.getEntrepriseId().equals(clientComboBox.getValue())).collect(Collectors.toList()).get(0);


        if (produitComboBox.getSelectionModel().getSelectedItem() != null ) {
            myproduit=DisplayProduits.stream().filter(produit -> produit.getProduitNom().equals(produitComboBox.getSelectionModel().getSelectedItem())).collect(Collectors.toList()).get(0);
            myproduit.setQuantite(Integer.parseInt(quantiteTextField.getText()));
            this.produits.add(myproduit);
            ShopingCarte.getChildren().clear();
            for (Produit pr: this.produits) {
                ShopingCarte.getChildren().add(new Label(pr.getProduitNom()));
            }

        }

        }catch (SQLException |NumberFormatException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void AddCommande(){
        LoyaltyCard loyaltyCard;
        Client client;

        try {
            ClientDaoImpl clientDao = new ClientDaoImpl();

            client=clientDao.selectAll().stream().filter(cl-> cl.getEntrepriseId().equals(clientComboBox.getValue())).collect(Collectors.toList()).get(0);

            LoyaltyCardDaoImpl loyaltyCardDao = new LoyaltyCardDaoImpl();

            loyaltyCard = loyaltyCardDao.findByOwner(client.getEntrepriseId());
            AgentCommercialDaoImpl agentCommercialDao = new AgentCommercialDaoImpl();
            CommandeDaoImpl commandeDao = new CommandeDaoImpl();
            commandeDao.ajoutCommande(loyaltyCard,
                    agentCommercialDao.getAgentByCNE(CurrentUser.getLoggedInCommercial()),
                    produits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProduitDaoImpl produitDao = new ProduitDaoImpl();
        DisplayProduits = produitDao.selectAll();
        System.out.println(DisplayProduits);
        produitComboBox.setItems(FXCollections.observableArrayList(DisplayProduits.stream().map(produit -> produit.getProduitNom()).collect(Collectors.toList())));
        ClientDaoImpl clientDao = new ClientDaoImpl();
        DisplayClients = clientDao.selectAll();
        clientComboBox.setItems(FXCollections.observableArrayList(DisplayClients.stream().map(client-> client.getEntrepriseId()).collect(Collectors.toList())));
    }
}
