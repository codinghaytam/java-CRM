package org.example.crm.dao.impl;

import org.example.crm.dao.AgentCommercialDao;
import org.example.crm.models.Lead;
import org.example.crm.models.Supervisor;
import org.example.crm.util.DatabaseConnection;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class AgentCommercialDaoImpl  implements AgentCommercialDao {
    private static final Logger LOGGER = Logger.getLogger(AgentCommercialDaoImpl.class.getName());
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
    public boolean ajoutLead(Lead lead) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String entrepriseId = lead.getEntrepriseId();
            String entrepriseName = lead.getEntrepriseName();
            String headquarters = lead.getHeadquarters();
            String phone = lead.getPhone();
            String email = lead.getEmail();
            String sql = "INSERT INTO leads(entrepriseId,entrepriseName,headquarters,phone,email) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,entrepriseId);
            stmt.setString(2,entrepriseName);
            stmt.setString(3,headquarters);
            stmt.setString(4,phone);
            stmt.setString(5,email);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(); // Pour diagnostiquer l'erreur
            return false;
        }
        return true;
    }
    @Override
    public List<Lead> afficheLead(){
        List<Lead> leads = new LinkedList<Lead>();
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = "SELECT * FROM leads";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Lead lead = new Lead(rs.getString("entrepriseId"),
                                     rs.getString("entrepriseName"),
                                     rs.getString("headquarters"),
                                     rs.getString("phone"),
                                     rs.getString("email"));
                					
                leads.add(lead);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return leads;
    }
    @Override
    public boolean deleteLead(String entrepriseId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM leads WHERE entrepriseId = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, entrepriseId);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
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
    public Supervisor getAgentByCNE(String CNE) {
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