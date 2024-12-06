package org.example.crm.dao;

import java.util.List;

import org.example.crm.models.Lead;

public interface LeadDao {
    public boolean ajoutLead(Lead lead);
    public List<Lead> afficheLead();
    public boolean deleteLead(String entrepriseId);
}
