package org.example.crm.dao.impl;

import org.example.crm.dao.AgentCommercialDao;
import org.example.crm.models.Lead;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AgentCommercialDaoImpl  implements AgentCommercialDao {
    @Override
    public boolean demandeLoyeltyCard(Lead lead, String description){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO demande (leadId, description) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,lead.getEntrepriseId());
            stmt.setString(2,description);
            stmt.executeUpdate();
        }catch(SQLException e){
            return false;
        }
        return true;
    }
    @Override
    public boolean ajoutLead(Lead lead) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String entrepriseId = lead.getEntrepriseId();
            String entrepriseName = lead.getEntrepriseName();
            String headquarters = lead.getHeadquarters();
            String phone = lead.getPhone();
            String email = lead.getEmail();
            String sql = "INSERT INTO Lead(entrepriseId,entrepriseName,headquarters,phone,email) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,entrepriseId);
            stmt.setString(2,entrepriseName);
            stmt.setString(3,headquarters);
            stmt.setString(4,phone);
            stmt.setString(5,email);
            stmt.executeUpdate();
        }catch(SQLException e){
            return false;
        }
        return true;
    }

    public List<Lead> afficheLead(){
        List<Lead> leads = new LinkedList<Lead>();
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = "SELECT * FROM Lead";
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
}
