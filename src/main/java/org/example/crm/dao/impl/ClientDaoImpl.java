package org.example.crm.dao.impl;

import org.example.crm.dao.ClientDao;
import org.example.crm.models.Client;
import org.example.crm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    @Override
    public boolean addClient(Client client) {
        Connection conn = null;
        try{
            conn = DatabaseConnection.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Client> selectAll() {
        return null;
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