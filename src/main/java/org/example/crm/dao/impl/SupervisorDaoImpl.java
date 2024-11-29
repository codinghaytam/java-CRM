package org.example.crm.dao.impl;

import lombok.NoArgsConstructor;
import org.example.crm.dao.SupervisorDao;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Supervisor;
import org.example.crm.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.NoArgsConstructor;
import org.example.crm.dao.SupervisorDao;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Supervisor;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor

public class SupervisorDaoImpl implements SupervisorDao {
    private static final Logger LOGGER = Logger.getLogger(SupervisorDaoImpl.class.getName());
    @Override
    public boolean addAgent(AgentCommercial agent) {
        final String query = "INSERT INTO agent_commercial (CNE, nom, prenom, password, supervisor_CNE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, agent.getCNE());
            stmt.setString(2, agent.getNom());
            stmt.setString(3, agent.getPrenom());
            // Hash the password before saving it
            String hashedPassword = BCrypt.hashpw(agent.getPassword(), BCrypt.gensalt());
            stmt.setString(4, hashedPassword);
            stmt.setString(5, agent.getSupervisor_id());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding agent: {0}", agent.getCNE());
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return false;
    }

    @Override
    public void validateDemande(int demandeId) {
        final String query = "UPDATE demande SET status = 'VALIDATED' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating demande ID: {0}", demandeId);
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
    }

    @Override
    public List<String> afficheDemandes() {
        final List<String> demandes = new ArrayList<>();
        final String query = "SELECT id, status FROM demande";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                demandes.add(String.format("Demande ID: %d, Status: %s", rs.getInt("id"), rs.getString("status")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching demandes", e);
        }
        return demandes;
    }
    @Override
    public List<AgentCommercial> showAgents() {
        final List<AgentCommercial> agents = new ArrayList<>();
        final String query = "SELECT CNE, nom, prenom, password, supervisor_CNE FROM agent_commercial";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                agents.add(new AgentCommercial(
                        rs.getString("CNE"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("password"),
                        rs.getString("supervisor_CNE")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching agents", e);
            throw new RuntimeException(e); // Optional: Re-throw as a runtime exception
        }
        return agents;
    }
    @Override	
    public boolean deleteAgent(String agentCNE) {
        final String query = "DELETE FROM agent_commercial WHERE CNE = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, agentCNE);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting agent CNE: {0}", agentCNE);
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return false;
    }
    @Override
    public boolean verifyLogin(String login, String password) {
        final String query = "SELECT 1 FROM supervisor WHERE CNE = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error verifying login for CNE: {0}", login);
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return false;
    }
    @Override
    public Supervisor getSupervisorByCNE(String CNE) {
        final String query = "SELECT CNE, nom, prenom, password FROM supervisor WHERE CNE = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, CNE);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Supervisor(
                            rs.getString("CNE"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching supervisor with CNE: {0}", CNE);
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return null;
    }
}