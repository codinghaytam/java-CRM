package org.example.crm.dao.impl;

import lombok.NoArgsConstructor;
import org.example.crm.dao.SupervisorDao;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Supervisor;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor

public class SupervisorDaoImpl implements SupervisorDao {

    @Override
    public boolean addAgent(AgentCommercial agent) {
        String query = "INSERT INTO agent_commercial (CNE, nom, prenom, password,supervisor_CNE) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, agent.getCNE());
            stmt.setString(2, agent.getNom());
            stmt.setString(3, agent.getPrenom());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getSupervisor_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void validateDemande(int demandeId) {
        String query = "UPDATE demande SET status = 'VALIDATED' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> afficheDemandes() {
        List<String> demandes = new ArrayList<>();
        String query = "SELECT * FROM demande";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                demandes.add("Demande ID: " + rs.getInt("id") + ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demandes;
    }

    @Override
    public List<AgentCommercial> showAgents() {
        List<AgentCommercial> agents = new ArrayList<>();
        String query = "SELECT * FROM agent_commercial";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Debug: Print the fetched data
                System.out.println("Fetched agent: " + rs.getString("CNE"));
                agents.add(new AgentCommercial(
                        rs.getString("CNE"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("password"),
                        rs.getString("supervisor_CNE")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return agents;
    }



    @Override
    public void deleteAgent(String agentCNE) {
        String query = "DELETE FROM agent_commercial WHERE CNE = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, agentCNE);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verifyLogin(String login , String password){
        String query = "SELECT * FROM supervisor WHERE CNE = ? AND password = ?";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,login);
            stmt.setString(2,password);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return true;
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Supervisor getSupervisorByCNE(String CNE) {
        String query = "SELECT * FROM supervisor WHERE CNE = ?";
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
            e.printStackTrace();
        }
        return null;
    }
}

