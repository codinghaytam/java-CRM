package org.example.crm.dao;

import org.example.crm.models.AgentCommercial;
import org.example.crm.models.Demande;
import org.example.crm.models.Lead;
import org.example.crm.models.LoyaltyCard;

public interface DemandeDao {
    public boolean demandeLoyeltyCard(AgentCommercial agent, Lead lead, LoyaltyCard card, String description);
    public boolean acceptRequest(Demande demande,AgentCommercial agent, Lead lead, LoyaltyCard card);
    public boolean rejectRequest(Demande demande,AgentCommercial agent, Lead lead, LoyaltyCard card);
}
