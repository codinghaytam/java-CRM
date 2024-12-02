package org.example.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class Commande {
	Produit produit;
	LoyaltyCard loyaltycard;
    String commandeId;
}