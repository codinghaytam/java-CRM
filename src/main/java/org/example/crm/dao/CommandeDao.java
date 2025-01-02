package org.example.crm.dao;

import java.util.List;
import java.util.Map;

import org.example.crm.models.AgentCommercial;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.models.Produit;

public interface CommandeDao {
	public boolean ajoutCommande(LoyaltyCard loyaltycard, AgentCommercial agent, List<Produit> produit);

    Map<String, Double> getTotalPricePerMonth();
}
