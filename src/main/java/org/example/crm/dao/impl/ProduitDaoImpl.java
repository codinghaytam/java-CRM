package org.example.crm.dao.impl;

import org.example.crm.dao.ProduitDao;
import org.example.crm.models.Produit;
import org.example.crm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDaoImpl implements ProduitDao {

    private Connection connection;
    @Override
    public List<Produit> selectAll() {
        List<Produit> produits = new ArrayList<>();
        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM produits");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getString("produitId"),
                        resultSet.getString("produitNom"),
                        resultSet.getString("produitsCategorie"),
                        resultSet.getFloat("prix"),
                        0
                ));
            }
            return produits;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}