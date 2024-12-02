package org.example.crm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.example.crm.dao.CommandeDao;
import org.example.crm.models.AgentCommercial;
import org.example.crm.models.LoyaltyCard;
import org.example.crm.models.Produit;
import org.example.crm.util.DatabaseConnection;

public class CommandeDaoImpl implements CommandeDao {		    
		    public boolean ajoutCommande(LoyaltyCard loyaltyCard, AgentCommercial agent, List<Produit> produits) {
		        Connection connection = null;
		        try {
		            connection = DatabaseConnection.getConnection();
		            connection.setAutoCommit(false); // Début de la transaction
		            
		            // Calculer le prix total avec remise
		            double prixTotal = 0.0;
		            for (Produit produit : produits) {
		                double prixProduit = produit.getPrix();
		                double remise = loyaltyCard.getCategoryDiscount().getOrDefault(produit.getCategorieId(), 0.0);
		                double prixRemise = prixProduit * (1 - remise / 100);
		                prixTotal += prixRemise;
		            }
		            
		            // Insérer dans la table commande
		            String commandeSQL = "INSERT INTO commande (commandeId, CNE, carteDeFideliteId, prixTotal) VALUES (?, ?, ?, ?)";
		            PreparedStatement commandeStmt = connection.prepareStatement(commandeSQL);
		            String commandeId = java.util.UUID.randomUUID().toString(); // Générer un UUID unique pour la commande
		            
		            commandeStmt.setString(1, commandeId);
		            commandeStmt.setString(2, agent.getCNE());
		            commandeStmt.setString(3, loyaltyCard.getCarteDeFideliteId());
		            commandeStmt.setDouble(4, prixTotal);
		            commandeStmt.executeUpdate();
		            
		            // Insérer les produits dans la table produitcommande
		            String produitCommandeSQL = "INSERT INTO produitcommande (commandeId, produitId, quantite) VALUES (?, ?, ?)";
		            PreparedStatement produitCommandeStmt = connection.prepareStatement(produitCommandeSQL);
		            
		            for (Produit produit : produits) {
		                produitCommandeStmt.setString(1, commandeId);
		                produitCommandeStmt.setString(2, produit.getProduitId());
		                produitCommandeStmt.setInt(3, produit.getQuantite()); // Quantité par défaut 1, à modifier si nécessaire
		                produitCommandeStmt.addBatch(); // Ajouter à la batch pour optimisation
		            }
		            
		            produitCommandeStmt.executeBatch(); // Exécuter les insertions en batch
		            
		            connection.commit(); // Valider la transaction
		            return true;
		            
		        } catch (SQLException e) {
		            try {
		                if (connection != null) connection.rollback(); // Annuler en cas d'erreur
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		            e.printStackTrace();
		            return false;
		        } finally {
		            try {
		                if (connection != null) {
		                    connection.setAutoCommit(true); // Restaurer le mode auto-commit
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		}

}