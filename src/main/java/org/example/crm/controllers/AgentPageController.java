package org.example.crm.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;
import org.example.crm.dao.impl.ClientDaoImpl;
import org.example.crm.dao.impl.LeadDaoImpl;
import org.example.crm.models.Client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AgentPageController  implements Initializable {

    @FXML
    private Button createLeadBtn;

    @FXML
    private Button showLeadsBtn;

    @FXML
    private Button deleteLeadsBtn;


    @FXML
    private TableView<Client> clientTableView = new TableView<>();
    @FXML
    private TableColumn<Client,String> telephone;

    @FXML
    private TableColumn<Client,String> name;
    @FXML
    private TableColumn<Client,String> adresse;
    @FXML
    private TableColumn<Client,String> email;

    @FXML
    private TableColumn<Client,String> clientid;

    @FXML
    private Label clientNumbers;
    @FXML
    private Label LeadNumbers;

    @FXML
    private Button showClientsBtn;


    // Méthode pour créer un Lead
    @FXML
    private void createLead() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/Agent/AddLead-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage LeadCreation = new Stage();
        LeadCreation.setTitle("Create Lead");
        LeadCreation.setScene(scene);
        LeadCreation.show();
    }

    // Méthode pour afficher les Leads
    @FXML
    private void showLeads() {
        MainController.navigateTo("view/Agent/showLeads-view.fxml", "Show Leads", showLeadsBtn);
    }

    @FXML
    private void showClients() {
        MainController.navigateTo("view/Agent/AgentPage-view.fxml", "Clients", showClientsBtn);
    }

    @FXML
    private void showCounts() {
        ClientDaoImpl clientDao = new ClientDaoImpl();
        LeadDaoImpl leadDao = new LeadDaoImpl();
        clientNumbers.setText(clientDao.selectAll().size() + "");
        LeadNumbers.setText(leadDao.afficheLead().size() + "");
    }


    private void displayAll(){
        ClientDaoImpl ClientsDao = new ClientDaoImpl();
        List<Client> table=ClientsDao.selectAll();
        ObservableList<Client> Clients = FXCollections.observableArrayList(table);
        clientid.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getEntrepriseId()));
        telephone.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getPhone()));
        name.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getEntrepriseName()));
        email.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getEmail()));
        adresse.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getHeadquarters()));
        // Create the Action column
        TableColumn<Client, Void> actionColumn = new TableColumn<>("Action");

        // Set the cell factory for the Action column
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    ClientsDao.deleteClient(client.getEntrepriseId()); // Call the delete method in your DAO
                    Clients.remove(client); // Remove the item from the ObservableList
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

        // Add the Action column to the TableView
        clientTableView.getColumns().add(actionColumn);

        clientTableView.setItems(Clients);
    }
    @FXML
    private void createClient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/Agent/AddClient-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage LeadCreation = new Stage();
        LeadCreation.setTitle("Create Client");
        LeadCreation.setScene(scene);
        LeadCreation.show();
    }
    @FXML
    private void ajouteCommand() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/Agent/Add-command.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage LeadCreation = new Stage();
        LeadCreation.setTitle("Create Commande");
        LeadCreation.setScene(scene);
        LeadCreation.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayAll();
        showCounts();
    }
}
