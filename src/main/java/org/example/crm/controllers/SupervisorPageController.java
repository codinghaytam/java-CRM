package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.util.CurrentUser;

import java.util.LinkedHashMap;
import java.util.Map;

public class SupervisorPageController {

    @FXML
    private Button manageAgentsBtn, manageLeadsBtn, reportsBtn, settingsBtn, logoutBtn ,manageRequestsBtn;;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Label totalAgents, nomAdmin ;


    private final SupervisorDaoImpl supervisorDao = new SupervisorDaoImpl();

    // Constants for navigation paths and titles
    private static final String MANAGE_AGENTS_VIEW = "view/Supervisor/ManageAgents-view.fxml";
    private static final String MANAGE_LEADS_VIEW = "view/Supervisor/ManageLeads-view.fxml";
    private static final String REPORTS_VIEW = "view/Supervisor/reports-view.fxml";
    private static final String SETTINGS_VIEW = "view/Supervisor/settings-view.fxml";
    private static final String LOGIN_VIEW = "view/hello-view.fxml";
    private static final String REQUESTS_VIEW = "view/Demandes/Requests-view.fxml";

    public void initialize() {
        setupChart();
        populateStats();
    }

    private void setupChart() {
        // Create and populate a series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Leads in 2024");

        // Define data points using LinkedHashMap to preserve order
        Map<String, Integer> dataPoints = new LinkedHashMap<>();
        dataPoints.put("January", 50);
        dataPoints.put("February", 75);
        dataPoints.put("March", 100);
        dataPoints.put("April", 150);
        dataPoints.put("May", 200);

        // Add data points to the series
        dataPoints.forEach((month, value) -> series.getData().add(new XYChart.Data<>(month, value)));

        // Add the series to the chart
        lineChart.getData().add(series);
    }


    private void populateStats() {
        // Update labels with dynamic data
        totalAgents.setText(String.valueOf(supervisorDao.showAgents().size()));
        nomAdmin.setText(CurrentUser.getLoggedInAdmin());
    }

    // Centralized navigation handler
    private void navigate(String fxmlPath, String title, boolean newWindow,Button btn) {
        MainController.navigateTo(fxmlPath, title , newWindow,btn);
    }

    @FXML
    private void HandleManageAgents() {
        navigate(MANAGE_AGENTS_VIEW, "Manage Agents",true,manageAgentsBtn);
    }

    @FXML
    private void handleRequests(){
        navigate(REQUESTS_VIEW,"DEMANDES",true , manageRequestsBtn);
    }

    @FXML
    private void HandleManageLeads() {
        navigate(MANAGE_LEADS_VIEW, "Manage Leads",true,manageLeadsBtn);
    }

    @FXML
    private void HandleReports() {
        navigate(REPORTS_VIEW, "Reports",true,reportsBtn);
    }

    @FXML
    private void HandleSettings() {
        navigate(SETTINGS_VIEW, "Settings",true,settingsBtn);
    }

    @FXML
    private void HandleLogout() {
        navigate(LOGIN_VIEW, "Login",false,logoutBtn);
    }
}
