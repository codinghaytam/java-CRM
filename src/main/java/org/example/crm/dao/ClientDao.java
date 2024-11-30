package org.example.crm.dao;

import org.example.crm.models.Client;
import org.example.crm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClientDao {
    public boolean addClient(Client client);
    public boolean updateClient(Client client);
    public boolean deleteClient(int id);
    public List<Client> selectAll();
}
