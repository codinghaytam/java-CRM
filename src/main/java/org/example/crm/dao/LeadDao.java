package org.example.crm.dao;

import org.example.crm.models.Lead;

import java.util.List;

public interface LeadDao {
    void addLead(Lead lead) throws Exception;

    void updateLead(String entrepriseId, Lead lead) throws Exception;

    void deleteLead(String entrepriseId) throws Exception;

    Lead getLeadById(String entrepriseId) throws Exception;

    List<Lead> getAllLeads() throws Exception;
}
