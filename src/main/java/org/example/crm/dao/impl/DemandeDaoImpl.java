package org.example.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.crm.dao.DemandeDAO;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Client;
import org.example.crm.models.Demande;
import org.example.crm.models.Lead;
import org.example.crm.util.DatabaseConnection;

public class DemandeDaoImpl implements DemandeDAO {
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

    @Override
    public boolean addDemande(Demande demande, AgentCommercial agent) {
        return false;
    }

    @Override
    public boolean updateClient(Demande demande) {
        return false;
    }

    @Override
    public boolean deleteClient(String id) {
        return false;
    }

    @Override
    public List<Client> selectAll() {
        return List.of();
    }
}
