package org.example.crm.dao;

import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Lead;
import org.example.crm.models.Supervisor;

import java.util.List;
public interface AgentCommercialDao {
    public boolean demandeLoyeltyCard(Lead lead, String description);
    public boolean ajoutLead(Lead lead);
    public List<Lead> afficheLead();
    public boolean deleteLead(String entrepriseId);
    boolean verifyLogin(String login, String password);
    AgentCommercial getAgentByCNE(String CNE);
}