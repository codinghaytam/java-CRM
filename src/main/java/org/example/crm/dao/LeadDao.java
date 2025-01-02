package org.example.crm.dao;

import org.example.crm.models.Lead;

import java.util.List;

public interface LeadDao {
    boolean ajoutLead(Lead lead);

    List<Lead> afficheLead();

    boolean deleteLead(String entrepriseId);
}
