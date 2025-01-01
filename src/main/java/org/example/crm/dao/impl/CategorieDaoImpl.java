package org.example.crm.dao.impl;
import org.example.crm.dao.CategorieDao;
import org.example.crm.models.Categorie;
import org.example.crm.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDaoImpl implements CategorieDao {
    private  Connection connection;

    public CategorieDaoImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategorie(Categorie categorie) throws Exception {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "INSERT INTO categories (categorieId, nomCategorie) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categorie.getId());
            statement.setString(2, categorie.getNom());
            statement.executeUpdate();
        }
    }

    @Override
    public void updateCategorie(String id, Categorie categorie) throws Exception {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "UPDATE categories SET nomCategorie = ? WHERE categorieId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categorie.getNom());
            statement.setString(2, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteCategorie(String id) throws Exception {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "DELETE FROM categories WHERE categorieId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Categorie getCategorieById(String id) throws Exception {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        System.out.println(connection);
        String sql = "SELECT categorieId, nomCategorie FROM categories WHERE categorieId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Categorie(
                            resultSet.getString("categorieId"),
                            resultSet.getString("nomCategorie")
                    );
                }
            }
        }
        return null; // Return null if no category is found
    }

    @Override
    public List<Categorie> getAllCategories() throws Exception {
        if(this.connection.isClosed()){
            this.connection = DatabaseConnection.getConnection();
        }
        String sql = "SELECT categorieId, nomCategorie FROM categories";
        List<Categorie> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                categories.add(new Categorie(
                        resultSet.getString("categorieId"),
                        resultSet.getString("nomCategorie")
                ));
            }
        }
        return categories;
    }
}
