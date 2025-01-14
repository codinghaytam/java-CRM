package org.example.crm.dao.impl;

import org.example.crm.dao.ClientDao;
import org.example.crm.models.Client;
import org.example.crm.util.CurrentUser;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    @Override
    public boolean addClient(Client client) {
        Connection conn = null;
        try{
            conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement("insert into clients values(?,?,?,?,?,?)");
            pr.setString(1,client.getLeadId());
            pr.setString(2,client.getName());
            pr.setString(3,client.getHeadQuarters());
            pr.setString(5,client.getEmail());
            pr.setString(4,client.getPhone());
            pr.setString(6, CurrentUser.getLoggedInCommercial());


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
            String sql = "select * from clients";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                result.add(new Client(
                        rs.getString("clientId"),
                        rs.getString("Name"),
                        rs.getString("headquarters"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("agentId")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteClient(String id) {
        try{
            String sql = "delete from clients where clientId =?";
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int num = ps.executeUpdate();
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
    public boolean updateClient(Client client) {
        return false;
    }


}