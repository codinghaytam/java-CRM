package org.example.crm.dao;

import org.example.crm.models.Lead;

public interface DemandeDao {
    public boolean demandeLoyeltyCard(Lead lead, String description);
}
