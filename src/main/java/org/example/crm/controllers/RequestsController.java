package org.example.crm.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.crm.HelloApplication;
import org.example.crm.dao.impl.RequestDaoImpl;
import org.example.crm.models.Request;
import org.example.crm.models.statut;

import java.util.Date;

public class RequestsController {

    @FXML private TableView<Request> requestsTable;
    @FXML private TableColumn<Request, String> columnId;
    @FXML private TableColumn<Request, String> columnLeadId;
    @FXML private TableColumn<Request, String> columnAgentId;
    @FXML private TableColumn<Request, String> columnLoyaltyCardId;
    @FXML private TableColumn<Request, String> columnStatus;
    @FXML private TableColumn<Request, Date> columnCreationDate;

    public void initialize() {
        RequestDaoImpl requestDao = new RequestDaoImpl();
        ObservableList<Request> requestList = FXCollections.observableArrayList(requestDao.showRequests());

        // Bind columns to Request properties
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        columnLeadId.setCellValueFactory(cellData -> cellData.getValue().leadIdProperty());
        columnAgentId.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty());
        columnLoyaltyCardId.setCellValueFactory(cellData -> cellData.getValue().loyaltyCardIdProperty());
        columnStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asString());
        columnCreationDate.setCellValueFactory(cellData -> cellData.getValue().creationDateProperty());

        requestsTable.setItems(requestList);

        // Add click listener to table rows
        requestsTable.setRowFactory(tv -> {
            TableRow<Request> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) { // Detect double click
                    Request selectedRequest = row.getItem();
                    openDetailsView(selectedRequest); // Call method to open the details view
                }
            });
            return row;
        });
    }

    // Method to open a new view with request details
    private void openDetailsView(Request request) {
        //System.out.println(HelloApplication.class.getResource("view/Demandes/RequestDetails.fxml"));
        try {
            // Load the FXML for the details view
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/Demandes/RequestDetails.fxml"));
            Parent root = loader.load();


            // Get the controller of the details view
            RequestDetailsController controller = loader.getController();
            controller.setRequest(request); // Pass the selected Request to the controller

            // Show the details view in a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Request Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
