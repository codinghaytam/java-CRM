package org.example.crm.dao.impl;

import org.example.crm.dao.RequestDao;
import org.example.crm.models.Request;
import org.example.crm.models.Lead;
import org.example.crm.models.statut;
import org.example.crm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {
    @Override
    public List<Request> showRequests() {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT * FROM DEMANDES WHERE statut='PENDING'";  // Ensure the status is quoted as a string value

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Add each Request to the list
                requests.add(new Request(
                        rs.getString("demandeId"),
                        rs.getString("lead_id"),
                        rs.getString("agent_id"),
                        rs.getString("card_id"),
                        statut.valueOf(rs.getString("statut")),  // Assuming 'statut' is an enum
                        rs.getDate("dateDeCreation"),  // If it's stored as a DATE type
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("error dyal data ");
        }

        return requests;
    }

    @Override
    public boolean acceptRequest(String demandeId, String leadId, String loyaltyCardId) {
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String updateDemandeStatusQuery = "UPDATE demandes SET statut = ? WHERE demandeId = ?";
            try (PreparedStatement updateDemandeStmt = connection.prepareStatement(updateDemandeStatusQuery)) {
                updateDemandeStmt.setString(1, "APPROVED");
                updateDemandeStmt.setString(2, demandeId);
                updateDemandeStmt.executeUpdate();
            }

            String updateLeadStatusQuery = "UPDATE leads SET status = ? WHERE leadId = ?";
            try (PreparedStatement updateLeadStmt = connection.prepareStatement(updateLeadStatusQuery)) {
                updateLeadStmt.setString(1, "CLOSED_WON");
                updateLeadStmt.setString(2, demandeId);
                updateLeadStmt.executeUpdate();
            }

            String getLeadQuery = "SELECT * FROM leads WHERE leadId = ?";
            String updateLeadQuery = "UPDATE leads SET status = 'CLOSED_WON' WHERE leadId = ?";

            Lead current_lead = new Lead();

            try (PreparedStatement getLeadStmt = connection.prepareStatement(getLeadQuery);
                 PreparedStatement updateLeadStmt = connection.prepareStatement(updateLeadQuery)) {

                // Step 1: Fetch the lead
                getLeadStmt.setString(1, leadId);
                ResultSet rs = getLeadStmt.executeQuery();
                if (rs.next()) {
                    current_lead.setLeadId(rs.getString("leadId"));
                    current_lead.setName(rs.getString("Name"));
                    current_lead.setHeadQuarters(rs.getString("HeadQuarters"));
                    current_lead.setEmail(rs.getString("Email"));
                    current_lead.setPhone(rs.getString("Phone"));
                    current_lead.setAgentId(rs.getString("AgentId"));
                    System.out.println("Lead fetched successfully: " + current_lead);
                } else {
                    System.out.println("No lead found with the specified ID.");
                    return false; // Exit if no lead is found
                }

                // Step 2: Update the lead's status
                updateLeadStmt.setString(1, leadId);
                int rowsAffected = updateLeadStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Lead status updated to CLOSED_WON successfully.");
                } else {
                    System.out.println("Failed to update lead status.");
                }
            }


            String insertClientQuery = """
                INSERT INTO clients (clientId, name, headquarters, phone, email, agentId)
                VALUES (?, ?, ?, ?, ?, ?)
            """;

            try (PreparedStatement insertClientStmt = connection.prepareStatement(insertClientQuery)) {
                insertClientStmt.setString(1, current_lead.getLeadId());
                insertClientStmt.setString(2, current_lead.getName());
                insertClientStmt.setString(3, current_lead.getHeadQuarters());
                insertClientStmt.setString(4, current_lead.getPhone());
                insertClientStmt.setString(5, current_lead.getEmail());
                insertClientStmt.setString(6, current_lead.getAgentId());
                insertClientStmt.executeUpdate();
            }


            String updateCardStatusQuery = """
                UPDATE carteDeFidelite SET statut = ? WHERE carteDeFideliteId = ?
            """;
            try (PreparedStatement updateCardStmt = connection.prepareStatement(updateCardStatusQuery)) {
                updateCardStmt.setString(1, "active");
                updateCardStmt.setString(2, loyaltyCardId);
                updateCardStmt.executeUpdate();
            }


            // Valider la transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                // En cas d'erreur, annuler la transaction
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Réactiver l'auto-commit
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean rejectRequest(String demandeId, String leadId, String loyaltyCardId){
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String updateDemandeStatusQuery = "UPDATE demandes SET statut = ? WHERE demandeId = ?";
            try (PreparedStatement updateDemandeStmt = connection.prepareStatement(updateDemandeStatusQuery)) {
                    updateDemandeStmt.setString(1, "REJECTED");
                updateDemandeStmt.setString(2, demandeId);
                updateDemandeStmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                // En cas d'erreur, annuler la transaction
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Réactiver l'auto-commit
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



}
