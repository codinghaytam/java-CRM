package org.example.crm.dao.impl;

import org.example.crm.dao.AgentCommercialDao;
import org.example.crm.models.AgentCommercial;

import org.example.crm.util.DatabaseConnection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AgentCommercialDaoImpl  implements AgentCommercialDao {
    private static final Logger LOGGER = Logger.getLogger(AgentCommercialDaoImpl.class.getName());
    @Override
    public boolean verifyLogin(String login, String password) {
        final String query = "SELECT 1 FROM agent_commercial WHERE CNE = ? AND password = ?";
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
    public AgentCommercial getAgentByCNE(String CNE) {
        final String query = "SELECT * FROM agent_commercial WHERE CNE = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, CNE);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AgentCommercial(
                            rs.getString("CNE"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("password"),
                            rs.getString("supervisor_CNE")
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