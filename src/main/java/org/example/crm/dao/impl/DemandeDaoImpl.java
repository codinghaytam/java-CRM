package org.example.crm.dao.impl;

import org.example.crm.dao.DemandeDAO;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Client;
import org.example.crm.models.Demande;
import org.example.crm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DemandeDaoImpl implements DemandeDAO {
    @Override
    public boolean addDemande(Demande demande, AgentCommercial agent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement("insert into demande (entrepriseId,CNE,carteDeFideliteId,statut) values(?,?,?,?,?,?)");
            stmt.setString(2,demande.getClient().getEntrepriseId());
            stmt.setString(3,agent.getCNE());
            stmt.setString(4,demande.getClient().getLoyaltyCard().getCarteDeFideliteId());
            stmt.setString(5,demande.getStatus().toString());
            int isvalid=stmt.executeUpdate();
            if(isvalid==1){
                return true;
            }
            else {
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
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
