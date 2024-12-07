package org.example.crm.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.crm.dao.AgentCommercialDao;
import org.example.crm.dao.DemandeDAO;
import org.example.crm.dao.LoyaltyCardDao;
import org.example.crm.dao.impl.*;
import org.example.crm.models.*;
import org.example.crm.util.CurrentUser;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AddClientController implements Initializable {

    @FXML
    private ComboBox<String> leadComboBox;
    @FXML
    private VBox CardInfo;
    @FXML
    private Map<String,TextField> Inputs = new TreeMap<>();
    @FXML
    private Label errorLabel;

    private List<Categorie> categories;

    @FXML
    private void AddClient() throws Exception {
        LeadDaoImpl leadDao  = new LeadDaoImpl();
        LoyaltyCard card = new LoyaltyCard(UUID.randomUUID().toString()) ;
        LoyaltyCardDaoImpl loyaltyCardDao = new LoyaltyCardDaoImpl();
        boolean input_valid = true;
        Map<String,Double> values = new HashMap<>();
        for(TextField Input : Inputs.values()){
            try {
                Double.parseDouble(Input.getText());

            } catch (RuntimeException e) {
                errorLabel.setText("enter a valid promotion");
                input_valid = false;
            }
        }
        if(input_valid && leadComboBox.getValue() != null){
            for(String key_ : Inputs.keySet()){
                values.put(key_,Double.parseDouble(Inputs.get(key_).getText()));
            }
            System.out.println(values);
            card.setCategoryDiscount(values);
            Client client = new Client(leadDao.afficheLead().stream().filter(lead -> lead.getEntrepriseId().equals(leadComboBox.getValue())).collect(Collectors.toList()).get(0),card);
            DemandeDaoImpl demandDao = new DemandeDaoImpl();
            CategorieDaoImpl categorieDao = new CategorieDaoImpl();
            ClientDaoImpl clientDao = new ClientDaoImpl();
            AgentCommercialDaoImpl agentCommercialDao = new AgentCommercialDaoImpl();
            clientDao.addClient(client);
            loyaltyCardDao.save(card,client.getEntrepriseId());
            demandDao.addDemande(new Demande(client,Status.enAttente), agentCommercialDao.getAgentByCNE(CurrentUser.getLoggedInCommercial()));
            for(String key__ : card.getCategoryDiscount().keySet()){
                System.out.println(key__);
               String CategoryId= categories.stream().filter(categorie -> categorie.getNom().equals(key__)).collect(Collectors.toList()).get(0).getId();
               loyaltyCardDao.saveDiscount(card.getCarteDeFideliteId(), CategoryId, card.getCategoryDiscount().get(key__));
            }
            errorLabel.setText("Demande de creation client est envoyer");

        }
        else{
            errorLabel.setText("erreur de saisie");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeadDaoImpl leadDao  = new LeadDaoImpl();
        CategorieDaoImpl CategorieDao = new CategorieDaoImpl();
        try {
            leadComboBox.setItems(FXCollections.observableArrayList(leadDao.afficheLead().stream().map(Lead::getEntrepriseId).collect(Collectors.toSet())));
            this.categories=CategorieDao.getAllCategories();
            for (int i = 0; i < this.categories.size(); i++) {
                Label label = new Label(CategorieDao.getAllCategories().get(i).getNom());
                TextField textField = new TextField();
                Inputs.put(CategorieDao.getAllCategories().get(i).getNom(),textField);
                textField.setPromptText("Enter " + this.categories.get(i).getNom().toLowerCase());

                // Add label and text field to the layout
                CardInfo.getChildren().addAll(label, textField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
