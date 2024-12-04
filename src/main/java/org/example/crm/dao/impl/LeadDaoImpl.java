package org.example.crm.dao.impl;

import org.example.crm.dao.LeadDao;
import org.example.crm.models.Lead;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeadDaoImpl implements LeadDao {
    private  Connection connection;

    public LeadDaoImpl() {
        try{
            this.connection = DatabaseConnection.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLead(Lead lead) throws Exception {
        String sql = "INSERT INTO leads (entrepriseId, entrepriseName, headquarters, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lead.getEntrepriseId());
            statement.setString(2, lead.getEntrepriseName());
            statement.setString(3, lead.getHeadquarters());
            statement.setString(4, lead.getPhone());
            statement.setString(5, lead.getEmail());
            statement.executeUpdate();
        }
    }

    @Override
    public void updateLead(String entrepriseId, Lead lead) throws Exception {
        String sql = "UPDATE leads SET entrepriseName = ?, headquarters = ?, phone = ?, email = ? WHERE entrepriseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lead.getEntrepriseName());
            statement.setString(2, lead.getHeadquarters());
            statement.setString(3, lead.getPhone());
            statement.setString(4, lead.getEmail());
            statement.setString(5, entrepriseId);
            statement.executeUpdate();
        }
    }



    @Override
    public void deleteLead(String entrepriseId) throws Exception {
        String sql = "DELETE FROM leads WHERE entrepriseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entrepriseId);
            statement.executeUpdate();
        }
    }

    @Override
    public Lead getLeadById(String entrepriseId) throws Exception {
        String sql = "SELECT entrepriseId, entrepriseName, headquarters, phone, email FROM leads WHERE entrepriseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entrepriseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Lead(
                            resultSet.getString("entrepriseId"),
                            resultSet.getString("entrepriseName"),
                            resultSet.getString("headquarters"),
                            resultSet.getString("phone"),
                            resultSet.getString("email")
                    );
                }
            }
        }
        return null; // Return null if no lead is found
    }

    @Override
    public List<Lead> getAllLeads() throws Exception {
        String sql = "SELECT entrepriseId, entrepriseName, headquarters, phone, email FROM leads";
        List<Lead> leads = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                leads.add(new Lead(
                        resultSet.getString("entrepriseId"),
                        resultSet.getString("entrepriseName"),
                        resultSet.getString("headquarters"),
                        resultSet.getString("phone"),
                        resultSet.getString("email")
                ));
            }
        }
        return leads;
    }
}
