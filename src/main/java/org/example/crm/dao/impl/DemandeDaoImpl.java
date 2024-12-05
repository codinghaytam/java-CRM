package org.example.crm.dao.impl;

import org.example.crm.dao.DemandeDao;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Demande;
import org.example.crm.models.Lead;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemandeDaoImpl implements DemandeDao {
    private static final Logger LOGGER = Logger.getLogger(DemandeDaoImpl.class.getName());
    @Override
    public boolean demandeLoyeltyCard(AgentCommercial agent, Lead lead, LoyaltyCard card, String description) {
        String sql = "INSERT INTO demande (CIN,entrepriseId, carteDeFideliteId, description) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getCNE());
            stmt.setString(2, lead.getEntrepriseId());
            stmt.setString(3, card.getCarteDeFideliteId());
            stmt.setString(4, description);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error in demandeLoyeltyCard", e);
            return false;
        }
    }
    @Override
    public boolean acceptRequest(Demande demande, AgentCommercial agent, Lead lead, LoyaltyCard card) {
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String updateDemandeStatusQuery = "UPDATE demandes SET statut = ? WHERE demandeId = ?";
            try (PreparedStatement updateDemandeStmt = connection.prepareStatement(updateDemandeStatusQuery)) {
                updateDemandeStmt.setString(1, "approuvee");
                updateDemandeStmt.setString(2, demande.getDemandeId());
                updateDemandeStmt.executeUpdate();
            }

            String insertClientQuery = """
                INSERT INTO clients (clientId, nom, telephone, email, employeeId)
                VALUES (?, ?, ?, ?, ?)
            """;
            String clientId = lead.getEntrepriseId(); // Générer un UUID pour le client
            try (PreparedStatement insertClientStmt = connection.prepareStatement(insertClientQuery)) {
                insertClientStmt.setString(1, clientId);
                insertClientStmt.setString(2, lead.getEntrepriseName());
                insertClientStmt.setString(3, lead.getPhone());
                insertClientStmt.setString(4, lead.getEmail());
                insertClientStmt.setString(5, agent.getCNE());
                insertClientStmt.executeUpdate();
            }


            String updateCardStatusQuery = """
                UPDATE carteDeFidelite SET statut = ? WHERE carteDeFideliteId = ?
            """;
            try (PreparedStatement updateCardStmt = connection.prepareStatement(updateCardStatusQuery)) {
                updateCardStmt.setString(1, "active");
                updateCardStmt.setString(2, card.getCarteDeFideliteId());
                updateCardStmt.executeUpdate();
            }


            String updateLeadStatusQuery = "UPDATE leads SET statut = ? WHERE leadId = ?";
            try (PreparedStatement updateLeadStmt = connection.prepareStatement(updateLeadStatusQuery)) {
                updateLeadStmt.setString(1, "converti");
                updateLeadStmt.setString(2, lead.getEntrepriseId());
                updateLeadStmt.executeUpdate();
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
    public boolean rejectRequest(Demande demande, AgentCommercial agent, Lead lead, LoyaltyCard card){
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String updateDemandeStatusQuery = "UPDATE demandes SET statut = ? WHERE demandeId = ?";
            try (PreparedStatement updateDemandeStmt = connection.prepareStatement(updateDemandeStatusQuery)) {
                updateDemandeStmt.setString(1, "rejettee");
                updateDemandeStmt.setString(2, demande.getDemandeId());
                updateDemandeStmt.executeUpdate();
            }


            String updateCardStatusQuery = """
                UPDATE carteDeFidelite SET statut = ? WHERE carteDeFideliteId = ?
            """;
            try (PreparedStatement updateCardStmt = connection.prepareStatement(updateCardStatusQuery)) {
                updateCardStmt.setString(1, "rejette");
                updateCardStmt.setString(2, card.getCarteDeFideliteId());
                updateCardStmt.executeUpdate();
            }


            String updateLeadStatusQuery = "UPDATE leads SET statut = ? WHERE leadId = ?";
            try (PreparedStatement updateLeadStmt = connection.prepareStatement(updateLeadStatusQuery)) {
                updateLeadStmt.setString(1, "ferme");
                updateLeadStmt.setString(2, lead.getEntrepriseId());
                updateLeadStmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}