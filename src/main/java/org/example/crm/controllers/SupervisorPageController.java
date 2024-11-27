package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SupervisorPageController {

    @FXML
    private Button manageAgentsBtn;

    @FXML
    private Button manageLeadsBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private LineChart<String, Number> lineChart;

    public void initialize() {
        // Create a series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Leads in 2024");

        // Add data to the series
        series.getData().add(new XYChart.Data<>("January", 50));
        series.getData().add(new XYChart.Data<>("February", 75));
        series.getData().add(new XYChart.Data<>("March", 100));
        series.getData().add(new XYChart.Data<>("April", 150));
        series.getData().add(new XYChart.Data<>("May", 200));

        // Add the series to the chart
        lineChart.getData().add(series);
    }


    @FXML
    private void HandleManageAgents() {
        // Handle action for managing agents (Navigate to manage agents page)
        MainController.navigateTo("view/Supervisor/ManageAgents-view.fxml", "Manage Agents", manageAgentsBtn);
    }

    @FXML
    private void HandleManageLeads() {
        // Handle action for managing leads (Navigate to manage leads page)
        MainController.navigateTo("view/Supervisor/ManageLeads-view.fxml", "Manage Leads", manageLeadsBtn);
    }

    @FXML
    private void HandleReports() {
        // Handle action for reports (Navigate to reports page)
        MainController.navigateTo("view/Supervisor/reports-view.fxml", "Reports", reportsBtn);
    }

    @FXML
    private void HandleSettings() {
        // Handle action for settings (Navigate to settings page)
        MainController.navigateTo("view/Supervisor/settings-view.fxml", "Settings", settingsBtn);
    }

    @FXML
    private void HandleLogout() {
        // Handle logout (Log the user out and navigate to the login page)
        MainController.navigateTo("view/hello-view.fxml", "Login", logoutBtn);
    }
}
