package org.example.crm.dao;

import org.example.crm.models.Produit;

import java.util.List;

public interface ProduitDao {
    List<Produit> selectAll();
    //Produit findById(int id);
    //void save(Produit produit);
    //void update(Produit produit);
    //void delete(int id);

}
