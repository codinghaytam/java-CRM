package org.example.crm.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.crm.dao.impl.ClientDaoImpl;
import org.example.crm.models.Client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AgentPageDisplayController implements Initializable {
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

    private void displayAll(){
        ClientDaoImpl ClientsDao = new ClientDaoImpl();
        List<Client> table=ClientsDao.selectAll();
        ObservableList<Client> Clients = FXCollections.observableArrayList(table);
        telephone.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getPhone()));
        name.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getEntreprise()));
        email.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getEmail()));
        adresse.setCellValueFactory(celldata->new ReadOnlyObjectWrapper<>(celldata.getValue().getHeadquarters()));
        clientTableView.setItems(Clients);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayAll();
    }
}
