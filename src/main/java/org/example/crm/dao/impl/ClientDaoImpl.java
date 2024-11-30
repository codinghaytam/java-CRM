package org.example.crm.dao.impl;

import org.example.crm.dao.ClientDao;
import org.example.crm.models.Client;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    @Override
    public boolean addClient(Client client) {
        Connection conn = null;
        try{
            conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement("insert into client values(?,?,?,?,?,?,?)");
            pr.setString(1,client.getEntrepriseId());
            pr.setString(2,client.getEntreprise());
            pr.setString(3,client.getEmail());
            pr.setString(4,client.getPhone());
            pr.setString(5,client.getHeadquarters());
            pr.setTimestamp(6, Timestamp.from(Instant.now()));
            pr.setString(7,null );


            int num = pr.executeUpdate();
            if(num > 0){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Client> selectAll() {
        List<Client> result = new ArrayList<>();
        try{
            String sql = "select * from client";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                result.add(new Client(
                        rs.getString("nom"),
                        rs.getString("id"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        null
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteClient(int id) {
        return false;
    }

    @Override
    public boolean updateClient(Client client) {
        return false;
    }
}