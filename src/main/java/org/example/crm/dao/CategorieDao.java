package org.example.crm.dao;

import org.example.crm.models.Categorie;

import java.util.List;

public interface CategorieDao {
    void addCategorie(Categorie categorie) throws Exception;

    void updateCategorie(String id, Categorie categorie) throws Exception;

    void deleteCategorie(String id) throws Exception;

    Categorie getCategorieById(String id) throws Exception;

    List<Categorie> getAllCategories() throws Exception;
}
