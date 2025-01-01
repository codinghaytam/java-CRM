package org.example.crm.dao;

import org.example.crm.models.Client;

import java.util.List;

public interface ClientDao {
    boolean addClient(Client client);

    List<Client> selectAll();

    boolean deleteClient(String id);

    boolean updateClient(Client client);
}
