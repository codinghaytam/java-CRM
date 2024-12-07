package org.example.crm.dao;

import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Client;
import org.example.crm.models.Demande;
import org.example.crm.models.Lead;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface DemandeDAO {
    boolean demandeLoyeltyCard(Lead lead, String description);

    public boolean addDemande(Demande demande, AgentCommercial agent);
    public boolean updateClient(Demande demande);
    public boolean deleteClient(String id);
    public List<Client> selectAll();
}

