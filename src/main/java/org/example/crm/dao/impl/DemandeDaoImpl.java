package org.example.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.crm.dao.DemandeDao;
import org.example.crm.models.Lead;
import org.example.crm.util.DatabaseConnection;

public class DemandeDaoImpl implements DemandeDao {
    private static final Logger LOGGER = Logger.getLogger(DemandeDaoImpl.class.getName());
    @Override
    public boolean demandeLoyeltyCard(Lead lead, String description) {
        String sql = "INSERT INTO demande (leadId, description) VALUES (?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lead.getEntrepriseId());
            stmt.setString(2, description);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error in demandeLoyeltyCard", e);
            return false;
        }
    }
}
