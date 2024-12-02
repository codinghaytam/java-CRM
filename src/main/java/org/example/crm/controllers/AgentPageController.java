package org.example.crm.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;
import org.example.crm.dao.impl.ClientDaoImpl;
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
        MainController.navigateTo("view/Agent/ShowLeads-view.fxml", "Show Leads", showLeadsBtn);
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
        clientTableView.setItems(Clients);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayAll();
    }
}
