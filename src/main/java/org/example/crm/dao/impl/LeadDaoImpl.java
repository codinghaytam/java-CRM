package org.example.crm.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.example.crm.dao.LeadDao;
import org.example.crm.models.Lead;
import org.example.crm.util.CurrentUser;
import org.example.crm.util.DatabaseConnection;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class LeadDaoImpl implements LeadDao {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(LeadDaoImpl.class.getName());
    @Override
    public boolean ajoutLead(Lead lead) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String entrepriseId = lead.getLeadId();
            String entrepriseName = lead.getName();
            String headquarters = lead.getHeadQuarters();
            String phone = lead.getPhone();
            String email = lead.getEmail();
            String agent_CNE = CurrentUser.getLoggedInCommercial();
            String sql = "INSERT INTO leads(LeadId,Name,headquarters,phone,email,agentId) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,entrepriseId);
            stmt.setString(2,entrepriseName);
            stmt.setString(3,headquarters);
            stmt.setString(4,phone);
            stmt.setString(5,email);
            stmt.setString(6,agent_CNE);
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
                Lead lead = new Lead(rs.getString("leadId"),
                        rs.getString("Name"),
                        rs.getString("headquarters"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("agentId"));

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

}