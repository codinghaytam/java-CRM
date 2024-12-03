package org.example.crm.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.crm.dao.impl.AgentCommercialDaoImpl;
import org.example.crm.dao.impl.LeadDaoImpl;
import org.example.crm.models.Lead;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowLeadsController implements Initializable {
    @FXML
    private TableView<Lead> leadTable;
    @FXML
    private TableColumn<Lead, String> nameColumn;
    @FXML
    private TableColumn<Lead, String> headquartersColumn;
    @FXML
    private TableColumn<Lead, String> phoneColumn;
    @FXML
    private TableColumn<Lead, String> emailColumn;
    @FXML
    private TableColumn<Lead, Void> deleteColumn;
    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadLeadData();
    }

    public void loadLeadData() {
        LeadDaoImpl dao = new LeadDaoImpl();
        ObservableList<Lead> leadList = FXCollections.observableArrayList(dao.afficheLead());

        // Configuration des colonnes
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEntrepriseName()));

        headquartersColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        headquartersColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getHeadquarters()));

        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPhone()));

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEmail()));

        // Configuration de la colonne de suppression
        deleteColumn.setCellFactory(param -> new TableCell<Lead, Void>() {
            private final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    Lead lead = getTableView().getItems().get(getIndex());
                    handleDeleteAction(lead);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Alimentation du tableau
        leadTable.setItems(leadList);
    }

    private void handleDeleteAction(Lead lead) {
    	LeadDaoImpl dao = new LeadDaoImpl();
        if (dao.deleteLead(lead.getEntrepriseId())) {
            // Supprimer le lead du tableau
            leadTable.getItems().remove(lead);
            System.out.println("Lead " + lead.getEntrepriseId() + " supprimé avec succès.");
            messageLabel.setText("Lead " + lead.getEntrepriseId() + " supprimé avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression du lead " + lead.getEntrepriseId());
            messageLabel.setText("Erreur lors de la suppression du lead " + lead.getEntrepriseId());
        }
    }
}