package org.example.crm.dao;

import org.example.crm.models.Lead;

import java.util.List;

public interface AgentCommercialDao {
    public boolean demandeLoyeltyCard(Lead lead, String description);
    public boolean ajoutLead(Lead lead);
    public List<Lead> afficheLead();
}
