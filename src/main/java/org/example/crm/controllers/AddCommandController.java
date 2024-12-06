package org.example.crm.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.crm.dao.impl.ClientDaoImpl;
import org.example.crm.dao.impl.LoyaltyCardDaoImpl;
import org.example.crm.dao.impl.ProduitDaoImpl;
import org.example.crm.models.Client;
import org.example.crm.models.Commande;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.models.Produit;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddCommandController implements Initializable {

    private Map<String,Integer> produits;
    private List<Produit> DisplayProduits;
    private List<Client> DisplayClients;

    @FXML
    private ComboBox<String> produitComboBox;
    private ComboBox<String> clientComboBox;
    @FXML
    private VBox ShopingCarte;
    @FXML
    private TextField quantiteTextField;

    private Commande commande;


    private void AddProduitToController() {
        Produit myproduit ;
        try {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            LoyaltyCardDaoImpl loyaltyCardDao = new LoyaltyCardDaoImpl();

        }catch (SQLException e){
            e.printStackTrace();
        }

        commande = new Commande(myproduit,);
        if (produitComboBox.getSelectionModel().getSelectedItem() != null) {
            myproduit=DisplayProduits.stream().filter(produit -> produit.getProduitNom().equals(produitComboBox.getSelectionModel().getSelectedItem())).collect(Collectors.toList()).get(0);
            this.produits.put(myproduit.getProduitNom(),Integer.parseInt(quantiteTextField.getText()));
            ShopingCarte.getChildren().clear();
            for (String keys: this.produits.keySet()) {
                ShopingCarte.getChildren().add(new Label(keys));
            }
            
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProduitDaoImpl produitDao = new ProduitDaoImpl();
        DisplayProduits = produitDao.selectAll();
        produitComboBox.setItems(FXCollections.observableArrayList(DisplayProduits.stream().map(produit -> produit.getProduitNom()).collect(Collectors.toSet())));
        ClientDaoImpl clientDao = new ClientDaoImpl();
        DisplayClients = clientDao.selectAll();
        clientComboBox.setItems(FXCollections.observableArrayList(DisplayClients.stream().map(client-> client.getEntrepriseId()).collect(Collectors.toList())));
    }
}
