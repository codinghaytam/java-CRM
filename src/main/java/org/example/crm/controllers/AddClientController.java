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
import org.example.crm.dao.LoyaltyCardDao;
import org.example.crm.dao.impl.*;
import org.example.crm.models.*;
import org.example.crm.util.CurrentUser;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class for adding a new client.
 * Implements Initializable to initialize the controller after its root element has been completely processed.
 */
public class AddClientController implements Initializable {

    @FXML
    private ComboBox<String> leadComboBox; // ComboBox for selecting a lead
    @FXML
    private VBox CardInfo; // VBox layout for displaying card information
    @FXML
    private Map<String, TextField> Inputs = new TreeMap<>(); // Map to store input fields for categories
    @FXML
    private Label errorLabel; // Label to display error messages

    private List<Categorie> categories; // List to store categories

    /**
     * Method to add a new client.
     * Validates inputs and saves the client and loyalty card information.
     * @throws Exception if an error occurs during the process
     */
    @FXML
    private void AddClient() throws Exception {
        LeadDaoImpl leadDao = new LeadDaoImpl();
        LoyaltyCard card = new LoyaltyCard(UUID.randomUUID().toString());
        LoyaltyCardDaoImpl loyaltyCardDao = new LoyaltyCardDaoImpl();
        boolean input_valid = true;
        Map<String, Double> values = new HashMap<>();

        // Validate inputs
        for (TextField Input : Inputs.values()) {
            try {
                Double.parseDouble(Input.getText());
            } catch (RuntimeException e) {
                errorLabel.setText("enter a valid promotion");
                input_valid = false;
            }
        }

        // If inputs are valid and a lead is selected
        if (input_valid && leadComboBox.getValue() != null) {
            for (String key_ : Inputs.keySet()) {
                values.put(key_, Double.parseDouble(Inputs.get(key_).getText()));
            }
            card.setCategoryDiscount(values);

            // Create a new client
            List<Lead> filteredLeads = leadDao.afficheLead().stream()
                    .filter(lead -> lead.getLeadId().equals(leadComboBox.getValue()))
                    .collect(Collectors.toList());
            System.out.println("filteredLeads = " + filteredLeads);
            System.out.println("filteredLeads.getFirst() = " + filteredLeads.getFirst());
            Client client = new Client(filteredLeads.getFirst(), card);
            System.out.println("client = " + client);

            RequestDaoImpl demandDao = new RequestDaoImpl();
            CategorieDaoImpl categorieDao = new CategorieDaoImpl();
            ClientDaoImpl clientDao = new ClientDaoImpl();
            AgentCommercialDaoImpl agentCommercialDao = new AgentCommercialDaoImpl();

            // Save the client and loyalty card information
            System.out.println("client.getLeadId() = " + client.getLeadId());
            loyaltyCardDao.save(card, client.getLeadId());
            for (String key__ : card.getCategoryDiscount().keySet()) {
                String CategoryId = categories.stream()
                        .filter(categorie -> categorie.getNom().equals(key__))
                        .toList().getFirst().getId();
                loyaltyCardDao.saveDiscount(card.getCarteDeFideliteId(), CategoryId, card.getCategoryDiscount().get(key__));
            }
            errorLabel.setText("Demande de creation client est envoyer");
        } else {
            errorLabel.setText("erreur de saisie");
        }
    }

    /**
     * Initializes the controller class.
     * Sets up the lead ComboBox and category input fields.
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LeadDaoImpl leadDao = new LeadDaoImpl();
        CategorieDaoImpl CategorieDao = new CategorieDaoImpl();
        try {
            // Set items for the lead ComboBox
            leadComboBox.setItems(FXCollections.observableArrayList(
                    leadDao.afficheLead().stream().map(Lead::getLeadId).collect(Collectors.toSet())));
            this.categories = CategorieDao.getAllCategories();

            // Create input fields for each category
            for (int i = 0; i < this.categories.size(); i++) {
                Label label = new Label(CategorieDao.getAllCategories().get(i).getNom());
                TextField textField = new TextField();
                Inputs.put(CategorieDao.getAllCategories().get(i).getNom(), textField);
                textField.setPromptText("Enter " + this.categories.get(i).getNom().toLowerCase());

                // Add label and text field to the layout
                CardInfo.getChildren().addAll(label, textField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}