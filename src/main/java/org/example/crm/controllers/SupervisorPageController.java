package org.example.crm.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.crm.dao.impl.CommandeDaoImpl;
import org.example.crm.dao.impl.LeadDaoImpl;
import org.example.crm.dao.impl.RequestDaoImpl;
import org.example.crm.dao.impl.SupervisorDaoImpl;
import org.example.crm.util.CurrentUser;

import java.util.Map;

public class SupervisorPageController {

    @FXML
    private Button manageAgentsBtn, manageLeadsBtn, reportsBtn, settingsBtn, logoutBtn, manageRequestsBtn;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private Label totalAgents, nomAdmin, totalLeads, pendingRequests;

    private final SupervisorDaoImpl supervisorDao = new SupervisorDaoImpl();
    private final LeadDaoImpl leadDao = new LeadDaoImpl();
    private final RequestDaoImpl requestDao = new RequestDaoImpl();
    private final CommandeDaoImpl commandeDao = new CommandeDaoImpl();

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
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Price per Month");

        Map<String, Double> dataPoints = commandeDao.getTotalPricePerMonth();

        dataPoints.forEach((month, total) -> series.getData().add(new XYChart.Data<>(month, total)));

        lineChart.getData().add(series);
    }

    private void populateStats() {
        totalAgents.setText(String.valueOf(supervisorDao.showAgents().size()));
        nomAdmin.setText(CurrentUser.getLoggedInAdmin());
        totalLeads.setText(String.valueOf(leadDao.afficheLead().size()));
        pendingRequests.setText(String.valueOf(requestDao.showRequests().size()));
    }

    private void navigate(String fxmlPath, String title, boolean newWindow, Button btn) {
        MainController.navigateTo(fxmlPath, title, newWindow, btn);
    }

    @FXML
    private void HandleManageAgents() {
        navigate(MANAGE_AGENTS_VIEW, "Manage Agents", true, manageAgentsBtn);
    }

    @FXML
    private void handleRequests() {
        navigate(REQUESTS_VIEW, "DEMANDES", true, manageRequestsBtn);
    }

    @FXML
    private void HandleManageLeads() {
        navigate(MANAGE_LEADS_VIEW, "Manage Leads", true, manageLeadsBtn);
    }

    @FXML
    private void HandleReports() {
        navigate(REPORTS_VIEW, "Reports", true, reportsBtn);
    }

    @FXML
    private void HandleSettings() {
        navigate(SETTINGS_VIEW, "Settings", true, settingsBtn);
    }

    @FXML
    private void HandleLogout() {
        navigate(LOGIN_VIEW, "Login", false, logoutBtn);
    }
}